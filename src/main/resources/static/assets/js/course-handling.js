/*----------------- Variables Definition -----------------*/
let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/");

/*-------- Modal Variables --------*/

/* 1- Modal variables for creating new course */
let courseTitle = document.querySelector("#course-title");
let startDate = document.querySelector("#start-date");
let finishDate = document.querySelector("#finish-date");
let createCourseBtn = document.querySelector("#create-course");
let teachersOption = document.querySelector("#teachers-name");
let finalCreateCourseBtn = document.querySelector("#confirm-creating-course");
let teacherDataListOption = ''; // Initialization is necessary. don't change this otherwise the list of teachers won't create.
let studentDataListOption = ''; // Initialization is necessary. don't change this otherwise the list of students won't create.
let errMsg = document.querySelector("#err-msg");
let selectedTeacherId = '';

/*-------- table Variables --------*/
let table = document.querySelector("table");
let th = `<tr >
                <th > حذف </th> 
                <th > ویرایش </th>
                <th > افزودن دانشجو </th>  
                <th > تعداد دانشجویان </th>  
                 <th > نام استاد </th>
                <th > تاریخ پایان دوره </th>
                <th > تاریخ شروع دوره </th>
                <th >عنوان دوره </th>
                <th >ردیف</th>                                
            </tr>`;
let tr;


/*----------------- Function Definition -----------------*/
function loadAllCourses() {
    fetchApi.get(url.concat("get-all-courses"))
        .then(data => {
            if (data.length !== 0) {
                console.log(data);
                document.querySelector("#nothing-msg").style.display = "none";
                let i = 0;
                tr = th;
                data.forEach(course => {
                    i++;
                    tr += `
                <tr> 
                    <td >
                    <button type='button' class="btn btn-danger"  onclick='deleteCourse(url.concat("delete-course/" , ${course.courseId}))'>
                    حذف
                    </button>
                     </td>
                    <td >              
                    <!-- Button trigger modal -->
                    <button type="button" onclick='setCourseInfoToModal(${course.courseId})'  class="btn btn-success" data-toggle="modal" data-target="#editing-course-modal">
                      ویرایش
                    </button>
                     </td>                       
                    <td>
                     <a href="/add-students"><button type="button" onclick='setCourseInfoToModal(${course.courseId})'  class="btn btn-success" data-toggle="modal" data-target="#adding-students-modal">
                    لیست دانشجویان
                    </button>
                    </a>
                    </td>
                    <td >${course.numberOfStudentsAssigned}</td>
                    <td >${course.teacher.firstName} ${course.teacher.lastName}</td>
                    <td >${course.finishDate}</td>
                    <td >${course.startDate}</td>
                    <td >${course.courseTitle}</td>
                    <td >${i}</td>
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
    sessionStorage.setItem("courseIdForAddingStudents", id);
}

function loadStudentsAndTeachers() {
    addTeachersToList();
    addStudentsToList()
}

function addTeachersToList() {
    teacherDataListOption = `<option id="-1" selected>-</option>`;
    fetchApi.get(url.concat("get-all-active-teachers"))
        .then(teachers => {
            teachers.forEach(teacher => {
                teacherDataListOption += `<option id="${teacher.teacherId}"> ${teacher.firstName} ${teacher.lastName}</option>`;
            });
            teachersOption.innerHTML = teacherDataListOption;
        });
}

function addStudentsToList() {
    fetchApi.get(url.concat("get-all-active-students"))
        .then(students => {
            console.log(students);

            let selectDiv = document.querySelector("#select-div");
            let select = document.createElement("select");
            select.id = "student-list";
            select.name = "multiselect[]";
            select.className="form-control text-right";
            select.setAttribute("multiple", "multiple");
            select.required = true;

            students.forEach(student => {
                studentDataListOption += `<option value="${student.email}" id="${student.studentId}"> ${student.firstName} ${student.lastName}</option>`;
            });
            select.innerHTML = studentDataListOption;
            selectDiv.appendChild(select);
            console.log("------------- students Info ---------------");
            console.log(studentDataListOption);
            console.log(select);

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
    teachersOption.innerHTML = '';
    addTeachersToList();

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

createCourseBtn.addEventListener("click",loadStudentsAndTeachers());

finalCreateCourseBtn.addEventListener("click", evt => {
    try {
        evt.preventDefault();
        selectedTeacherId = $("#teachers-name option:selected").attr("id");
        checkValidationForEmptyInputField();
        let startDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(startDate.value, 'START_DATE');
        let finishDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(finishDate.value, 'FINISH_DATE');
        checkDateValidation(startDateInEnglishNum, finishDateInEnglishNum);
        let newCourse = {
            "courseTitle": courseTitle.value,
            "teacherId": selectedTeacherId,
            "startDateJalali": startDateInEnglishNum,
            "finishDateJalali": finishDateInEnglishNum
        };
        console.log(newCourse);
        clearCourseInputs();
        fetchApi.post(url.concat("save-new-course"), newCourse);
        $("#create-course-modal").modal('hide');
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

