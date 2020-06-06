/*----------------- Variables Definition -----------------*/
let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/");

/*-------- Modal Variables --------*/

/* 1- Modal variables for creating new course */
let courseTitle = document.querySelector("#course-title");
let startDate = document.querySelector("#start-date");
let finishDate = document.querySelector("#finish-date");
let createCourseBtn = document.querySelector("#create-course");
let teachersSelect = document.querySelector("#teachers-name");
let finalCreateCourseBtn = document.querySelector("#confirm-creating-course");
let teacherDataListOption = ''; // Initialization is necessary. don't change this otherwise the list of teachers won't create.
let studentDataListOption = ''; // Initialization is necessary. don't change this otherwise the list of students won't create.
let studentsSelect = document.createElement("select");
let studentSelectDiv = document.querySelector("#select-div");
let errMsg = document.querySelector("#err-msg");
let selectedTeacherId = '';

/*-------- table Variables --------*/
let table = document.querySelector("table");
let th = `<tr class="text-center" >
                <th class="text-center"> حذف </th> 
                <th class="text-center"> ویرایش </th>
                <th class="text-center"> لیست دانشجویان </th>  
                <th class="text-center"> تعداد دانشجویان </th>  
                 <th class="text-center"> نام استاد </th>
                <th class="text-center"> تاریخ پایان دوره </th>
                <th class="text-center"> تاریخ شروع دوره </th>
                <th class="text-center">عنوان دوره </th>
                <th class="text-center">ردیف</th>                                
            </tr>`;
let tr;


/*----------------- Function Definition -----------------*/
function loadAllCourses() {
    fetchApi.get(url.concat("get-all-courses"))
        .then(data => {
            if (data.length !== 0) {
                document.querySelector("#nothing-msg").style.display = "none";
                let i = 0;
                tr = th;
                data.forEach(course => {
                    i++;
                    let rowNum = i;
                    rowNum = rowNum.toString().toPersianDigit();

                    let startDate_inPersianDigit = convertNumbersOfDate_EnglishToPersian(course.startDate);
                    let finishDate_inPersianDigit = convertNumbersOfDate_EnglishToPersian(course.finishDate);
                    let studentsCount_inPersianDigit = course.numberOfStudentsAssigned.toString().toPersianDigit();


                    tr += `
                <tr> 
                    <td >
                    <a style="color: #ff2b4b" class="cursor-pointer"  onclick='deleteCourse(url.concat("delete-course/" , ${course.courseId}))'>
                     <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                     </td>
                    <td >              
                    <!-- Button trigger modal -->
                    <a style="color: #378041" class="cursor-pointer" onclick='setCourseInfoToModal(${course.courseId})' data-toggle="modal" data-target="#create-course-modal">
                     <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                     </td>      
                          
                          
                                      
                    <td>
                     <a style="color: #378041" class="cursor-pointer" onclick='setCourseInfoToModal(${course.courseId})'>
                     <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                    </a>
                    </td>
                    
                    <td >${studentsCount_inPersianDigit}</td>
                    <td >${course.teacher.firstName} ${course.teacher.lastName}</td>
                    <td >${finishDate_inPersianDigit}</td>
                    <td >${startDate_inPersianDigit}</td>
                    <td >${course.courseTitle}</td>
                    <td > ${rowNum}</td>
                </tr>`;
                });
                table.innerHTML = tr;
            }
        })
}

function deleteCourse(url) {
    if (confirm("آیا از حذف این دوره مطمئن هستید؟")) {
        fetchApi.delete(url);
        location.reload()
    }
}

function setCourseInfoToModal(id) {
    sessionStorage.setItem("courseId", id);
    fetchApi.get(url.concat("get-course/", id))
        .then(data => {
            console.log(data);
            courseTitle.value = data.courseTitle;
            startDate.value = convertNumbersOfDate_EnglishToPersian(data.startDate);
            finishDate.value = convertNumbersOfDate_EnglishToPersian(data.finishDate);

            /* set teacher to input*/
            let optionsOfTeachers = teachersSelect.options;
            for (i = 0; i < teachersSelect.options.length; i++) {
                if (`${data.teacher.teacherId}` === optionsOfTeachers[i].id) teachersSelect.selectedIndex = i;
            }

            /* set student options to multi select */
            let studentEmailsArray = [];
            // fill students email array
            for (j = 0; j < data.students.length; j++) {
                studentEmailsArray.push(`${data.students[j].email}`);
            }
            // Set the student email array
            $("#student-list").val(studentEmailsArray);

            // Then refresh
            $("#student-list").multiselect("refresh");

        })
}

function loadStudentsAndTeachers() {
    addTeachersToList();
    addStudentsToList();
}

function addTeachersToList() {
    teacherDataListOption = `<option id="-1" selected>-</option>`;
    fetchApi.get(url.concat("get-all-active-teachers"))
        .then(teachers => {
            teachers.forEach(teacher => {
                teacherDataListOption += `<option id="${teacher.teacherId}"> ${teacher.firstName} ${teacher.lastName}</option>`;
            });
            teachersSelect.innerHTML = teacherDataListOption;
        });
}

function addStudentsToList() {
    fetchApi.get(url.concat("get-all-active-students"))
        .then(students => {
            studentsSelect.id = "student-list";
            studentsSelect.name = "multiselect[]";
            studentsSelect.className = "form-control text-right";
            studentsSelect.setAttribute("multiple", "multiple");
            studentsSelect.required = true;
            students.forEach(student => {
                studentDataListOption += `<option value="${student.email}" "> ${student.firstName} ${student.lastName} </option>`;
            });
            studentsSelect.innerHTML = studentDataListOption;
            studentSelectDiv.appendChild(studentsSelect);

            $(document).ready(function () {
                $('#student-list').multiselect({
                    includeSelectAllOption: true,
                    enableFiltering: true,
                    selectAllNumber: false,
                    nonSelectedText: "-",
                    enableCaseInsensitiveFiltering: true,
                    buttonWidth: '400px',
                    numberDisplayed: 100,
                    optionLabel: function (element) {
                        return $(element).html() + '(' + $(element).val() + ')';
                    },
                    includeResetOption: true,
                    includeResetDivider: true,
                    resetText: "حذف گزینه های انتخاب شده",
                    allSelectedText: "انتخاب تمام گزینه ها",
                    selectAllText: "انتخاب تمام گزینه ها",
                    maxHeight: 500,
                    dropUp: true
                });
            });

        });
}

function clearCourseInputs() {
    errMsg.textContent = '';
    courseTitle.value = "";
    startDate.value = "";
    finishDate.value = "";
    teachersSelect.innerHTML = '';
    addTeachersToList();

    /* Deselect selected students in select menu */
    $('#student-list')
        .multiselect("deselectAll", false)
        .multiselect("refresh");
}

function checkShamsi(inputDate) {
    const patt = /(13|14)([0-9][0-9])\/(((0?[1-6])\/((0?[1-9])|([12][0-9])|(3[0-1])))|(((0?[7-9])|(1[0-2]))\/((0?[1-9])|([12][0-9])|(30))))/g;
    let result = patt.test(inputDate);
    if (result) {
        let pos = inputDate.indexOf('/');
        let year = parseFloat(inputDate.substring(0, pos));
        let nextPos = inputDate.indexOf('/', pos + 1);
        let month = parseInt(inputDate.substring(pos + 1, nextPos));
        let day = parseInt(inputDate.substring(nextPos + 1));
        if (month === 12 && (year + 1) % 4 !== 0 && day === 30)  // kabise = 1379, 1383, 1387,... (year +1) divides on 4 remains 0
            result = false;
    }
    return result;
}

/* convert persian number to english*/
String.prototype.toEnglishDigit = function () {
    /* numbers following are persian numbers
    *  const find = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];*/
    const find = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
    const replace = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    let replaceString = this;
    let regex;
    for (let i = 0; i < find.length; i++) {
        regex = new RegExp(find[i], "g");
        replaceString = replaceString.replace(regex, replace[i]);
    }
    return replaceString;
};

String.prototype.toPersianDigit = function () {
    /* numbers following are persian numbers
    *  const replace = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];*/
    const replace = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
    const find = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    let replaceString = this;
    let regex;
    for (let i = 0; i < find.length; i++) {
        regex = new RegExp(find[i], "g");
        replaceString = replaceString.replace(regex, replace[i]);
    }
    return replaceString;
};

function convertNumbersOfDate_PersianToEnglish(persianNumDate, dateType) {
    let stringsDateArray = persianNumDate.split("/");
    let yyyy = stringsDateArray[0].toEnglishDigit();
    let mm = stringsDateArray[1].toEnglishDigit();
    let dd = stringsDateArray[2].toEnglishDigit();
    let resultDate = yyyy + '/' + mm + '/' + dd;
    if (!checkShamsi(resultDate)) {
        if (dateType === "START_DATE") {
            errMsg.textContent = ".تاریخ شروع دوره معتبر نیست";
            throw  "Invalid Start Date .";
        } else if (dateType === 'FINISH_DATE') {
            errMsg.textContent = ".تاریخ اتمام دوره معتبر نیست";
            throw "Invalid Finish Date.";
        }
    } else return resultDate;
}

function convertNumbersOfDate_EnglishToPersian(englishNumDate) {
    let stringsDateArray = englishNumDate.split("/");
    let yyyy = stringsDateArray[0].toPersianDigit();
    let mm = stringsDateArray[1].toPersianDigit();
    let dd = stringsDateArray[2].toPersianDigit();
    return yyyy + '/' + mm + '/' + dd;
}

function checkDateValidation(startDate, finishDate) {
    if (finishDate < startDate) {
        errMsg.textContent = "!تاریخ اتمام دوره نمی تواند قبل از تاریخ شروع دوره باشد";
        throw "Finish Date < Start Date";
    }
    return true;
}

function checkValidationForEmptyInputField() {
    if (CheckEmptyInput.isEmptyInputField(courseTitle.value)) {
        errMsg.textContent = "عنوان دوره را وارد کنید";
        throw "Empty Course Title!"
    }
    if (CheckEmptyInput.isEmptyInputField(startDate.value)) {
        errMsg.textContent = "تاریخ شروع دوره را وارد کنید";
        throw "Empty Start Date!"
    }
    if (CheckEmptyInput.isEmptyInputField(finishDate.value)) {
        errMsg.textContent = "تاریخ اتمام دوره را وارد کنید";
        throw "Empty Finish Date!"
    }
    if (selectedTeacherId === "-1") {
        errMsg.textContent = "استاد دوره را وارد کنید";
        throw "Empty Teacher Field!"
    }
}


/*----------------- Events Definition -----------------*/

document.addEventListener("DOMContentLoaded", loadAllCourses());

createCourseBtn.addEventListener("click", loadStudentsAndTeachers());

finalCreateCourseBtn.addEventListener("click", evt => {
    try {
        evt.preventDefault();
        selectedTeacherId = $("#teachers-name option:selected").attr("id");
        let selectedStudentsEmail = $('#student-list').val();
        checkValidationForEmptyInputField();
        let startDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(startDate.value, 'START_DATE');
        let finishDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(finishDate.value, 'FINISH_DATE');
        checkDateValidation(startDateInEnglishNum, finishDateInEnglishNum);
        let newCourse = "";
        let courseIdForEditing = sessionStorage.getItem("courseId");
        if (courseIdForEditing)
            newCourse = {
                "courseId": courseIdForEditing,
                "courseTitle": courseTitle.value,
                "teacherId": selectedTeacherId,
                "startDateJalali": startDateInEnglishNum,
                "finishDateJalali": finishDateInEnglishNum,
                "selectedStudentsEmail": selectedStudentsEmail
            };
        else
            newCourse = {
                "courseTitle": courseTitle.value,
                "teacherId": selectedTeacherId,
                "startDateJalali": startDateInEnglishNum,
                "finishDateJalali": finishDateInEnglishNum,
                "selectedStudentsEmail": selectedStudentsEmail
            };
        clearCourseInputs();
        fetchApi.post(url.concat("save-new-course"), newCourse);
        $("#create-course-modal").modal('hide');
        sessionStorage.removeItem("courseId");
        location.reload();
    } catch (e) {
        console.log(e);
    }
});

/* if you click exit btn (inside creating new course modal), this event would be fired.*/
document.querySelector("#exit-create-course-modal").addEventListener("click", (e) => {
    e.preventDefault();
    clearCourseInputs();
    $("#create-course-modal").modal('hide');
});

/* student - list
<a href="/add-students">
    <button type="button" onclick='setCourseInfoToModal(${course.courseId})'  class="btn btn-secondary" data-toggle="modal" data-target="#adding-students-modal">
    <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
    </button>
    </a>*/
