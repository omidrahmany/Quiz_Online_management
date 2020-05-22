/*----------------- Variables Definition -----------------*/
let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/");


/*-------- Modal Variables --------*/

/* 1- Modal variables for creating new course */

let courseTitle = document.querySelector("#course-title");
let startDate = document.querySelector("#start-date");
let finishDate = document.querySelector("#finish-date");
let teacherName = document.querySelector("#teacher");
let createCourseBtn = document.querySelector("#create-course");
let teacherDataList = document.querySelector("#teachers-name");
let finalCreateCourseBtn = document.querySelector("#confirm-creating-course");
let teacherDataListOption;


let firstName = document.querySelector("#first-name");
let lastName = document.querySelector("#last-name");
let username = document.querySelector("#username");
let roleStatus = document.querySelector("#role");
let email = document.querySelector("#email");
let enabledStatus = document.querySelector("#status");


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

function loadCreatingNewCourseModal() {
    teacherDataListOption = '';
    fetchApi.get(url.concat("get-all-active-teachers"))
        .then(teachersList => {
            teachersList.forEach(teacher => {
                teacherDataListOption += `
                <option>${teacher.firstName} ${teacher.lastName}</option>
                `
            });
            teacherDataList.innerHTML = teacherDataListOption;
        })
}


/*----------------- Event Definition -----------------*/
document.addEventListener("DOMContentLoaded", loadAllCourses());
createCourseBtn.addEventListener("click", loadCreatingNewCourseModal());
finalCreateCourseBtn.addEventListener("click", evt => {
    evt.preventDefault();
    console.log(teacherDataListOption);
    console.log(teacherName.value);
});

