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
let studentsOption = document.querySelector("#students-name");
let finalCreateCourseBtn = document.querySelector("#confirm-creating-course");
let teacherDataListOption=''; // Initialization is necessary. don't change this otherwise the list of teachers won't create.
let studentDataListOption=''; // Initialization is necessary. don't change this otherwise the list of students won't create.
let errMsg = document.querySelector("#err-msg");


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
                document.querySelector("#nothing-msg").style.display = "none";
                let i = 0;
                tr = th;
                data.forEach(course => {
                    i++;
                    tr += `
                <tr> 
                    <td >
                    <button type='button' class="btn btn-danger"  onclick='deleteCourse(deleteUrl.concat(${course.courseId}))'>
                    حذف
                    </button>
                     </td>
                    <td >              
                    <!-- Button trigger modal -->
                    <button type="button" onclick='setCourseInfoToModal(${course.courseId})'  class="btn btn-success" data-toggle="modal" data-target="#showModal">
                      ویرایش
                    </button>
                     </td>                       
                    <td> <button type="button" onclick='setCourseInfoToModal(${course.courseId})'  class="btn btn-success" data-toggle="modal" data-target="#showModal">
                    لیست دانشجویان
                    </button></td>
                    <td >تعداد</td>
                    <td >${course.teacherName}</td>
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

function clearErrMsg() {
    errMsg.textContent = "";
}

function addTeachersToList(){
    teacherDataListOption = `<option id="-1" selected>گزینه ای انتخاب نشده</option>`;
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
            students.forEach(student => {
                studentDataListOption += `<option value="${student.studentId}"> ${student.firstName} ${student.lastName}</option>`;
            });
            studentsOption.innerHTML = studentDataListOption;
            console.log("------------- students Info ---------------");
            console.log(studentDataListOption);
            console.log(studentsOption);
        });
    // studentsOption.bsMultiSelect();
   /* $(function(){
        $('#students-name').bsMultiSelect();
    });*/
    /*$("#students-name").bsMultiSelect({
        cssPatch: {
            choices: { columnCount: '3' },
        }
    });*/



    /*$(document).ready(function () {
        $('#students-name').multiselect({
            includeSelectAllOption: true,
            enableFiltering: true
        });
    });*/
}

function clearCourseInputs() {
    errMsg.textContent = '';
    courseTitle.value = "";
    startDate.value = "";
    finishDate.value = "";
    teachersOption.innerHTML = '';
    addTeachersToList();

}

function loadCreatingNewCourseModal() {
   addTeachersToList();
    // addStudentsToList();
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

/*----------------- Events Definition -----------------*/

document.addEventListener("DOMContentLoaded", loadAllCourses());

createCourseBtn.addEventListener("click", loadCreatingNewCourseModal());

finalCreateCourseBtn.addEventListener("click", evt => {
    try {
        evt.preventDefault();
        let startDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(startDate.value, 'START_DATE');
        let finishDateInEnglishNum = convertNumbersOfDate_PersianToEnglish(finishDate.value, 'FINISH_DATE');
        checkDateValidation(startDateInEnglishNum, finishDateInEnglishNum);
        /*let startDateGregorian = moment.from(startDateInEnglishNum, 'fa', 'YYYY/MM/DD').locale('en').format('MM-DD-YYYY');
        let finishDateGregorian = moment.from(finishDateInEnglishNum, 'fa', 'YYYY/MM/DD').locale('en').format('MM-DD-YYYY');*/
        let teacherId = $("#teachers-name option:selected").attr("id");
        let newCourse = {
            "courseTitle": courseTitle.value,
            "courseTeacherId": teacherId,
            "startDate": startDateInEnglishNum,
            "finishDate": finishDateInEnglishNum
        };
        console.log(newCourse);
        clearCourseInputs();
        // fetchApi.post(url.concat("save-new-course"),newCourse);
        $("#create-course-modal").modal('hide');
        // location.reload();
    } catch (e) {
        console.log(e);
    }
});

document.querySelector("#exit-create-course-modal").addEventListener("click",(e)=>{
    e.preventDefault();
    clearCourseInputs();
    $("#create-course-modal").modal('hide');
});

