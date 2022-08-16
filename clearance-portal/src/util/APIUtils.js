import { API_BASE_URL, POLL_LIST_SIZE, ACCESS_TOKEN } from "../constants";

const request = async (options) => {
  const headers = new Headers({
    "Content-Type": "application/json",
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      "Authorization",
      "Bearer " + localStorage.getItem(ACCESS_TOKEN)
    );
  }

  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  const response = await fetch(options.url, options);
  const json = await response.json();
  if (!response.ok) {
    return Promise.reject(json);
  }
  return json;
};

export function createPoll(pollData) {
  return request({
    url: API_BASE_URL + "/polls",
    method: "POST",
    body: JSON.stringify(pollData),
  });
}

export function castVote(voteData) {
  return request({
    url: API_BASE_URL + "/polls/" + voteData.pollId + "/votes",
    method: "POST",
    body: JSON.stringify(voteData),
  });
}

export function login(loginRequest) {
  return request({
    url: API_BASE_URL + "/auth/signin",
    method: "POST",
    body: JSON.stringify(loginRequest),
  });
}

export function signup(signupRequest) {
  return request({
    url: API_BASE_URL + "/auth/signup",
    method: "POST",
    body: JSON.stringify(signupRequest),
  });
}

export function checkUsernameAvailability(username) {
  return request({
    url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
    method: "GET",
  });
}
export function getAllBooks() {
  return request({
    url: API_BASE_URL + "/library/book/list",
    method: "GET",
  });
}
export function issueBook(book) {
  return request({
    url: API_BASE_URL + "/library/book/issue",
    method: "POST",
    body: JSON.stringify(book),
  });
}

export function addBook(book) {
  return request({
    url: API_BASE_URL + "/library/book/save",
    method: "POST",
    body: JSON.stringify(book),
  });
}  

    export function studentLibraryClearance(id) {
        return request({
          url: API_BASE_URL + "/library/student/clearance/" + id,
          method: "GET",
        });
      }

      export function gownClearance(id) {
        return request({
          url: API_BASE_URL + "/graduation/gown/clearance/" + id,
          method: "GET",
        });
      }
      export function financeClearance(id) {
        return request({
          url: API_BASE_URL + "/finance/clearance/" + id,
          method: "GET",
        });
      }
      

      export function gownIssuance(id) {
        return request({
          url: API_BASE_URL + "/graduation/gown/issuance/" + id,
          method: "GET",
        });
      }

      

export function checkStudentRegistrationStatus(regNo) {
  return request({
    url: API_BASE_URL + "/student/checkstudent?regNo=" + regNo,
    method: "GET",
  });
}

export function checkEmailAvailability(email) {
  return request({
    url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
    method: "GET",
  });
}

export function getCurrentUser() {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/user/me",
    method: "GET",
  });
}

export function getUserProfile(username) {
  return request({
    url: API_BASE_URL + "/users/" + username,
    method: "GET",
  });
}

export function getUserCreatedPolls(username, page, size) {
  page = page || 0;
  size = size || POLL_LIST_SIZE;

  return request({
    url:
      API_BASE_URL +
      "/users/" +
      username +
      "/polls?page=" +
      page +
      "&size=" +
      size,
    method: "GET",
  });
}

export function getUserVotedPolls(username, page, size) {
  page = page || 0;
  size = size || POLL_LIST_SIZE;

  return request({
    url:
      API_BASE_URL +
      "/users/" +
      username +
      "/votes?page=" +
      page +
      "&size=" +
      size,
    method: "GET",
  });
}

export function getAllStudents() {
  return request({
    url: API_BASE_URL + "/student/list",
    method: "GET",
  });
}
export function saveStudent(studentData) {
  return request({
    url: API_BASE_URL + "/student/save",
    method: "POST",
    body: JSON.stringify(studentData),
  });
}
export function getLibraryRecords() {
  return request({
    url: API_BASE_URL + "/library/list",
    method: "GET",
  });
}
export function getAllFinancialRecords() {
  return request({
    url: API_BASE_URL + "/finance/list",
    method: "GET",
  });
}
export function getAllIssuedGowns() {
  return request({
    url: API_BASE_URL + "/graduation/list",
    method: "GET",
  });
}
export function getAllPolls() {
  return request({
    url: API_BASE_URL + "/graduation/list",
    method: "GET",
  });
}

export function generateGraduationList() {
  return request({
    url: API_BASE_URL + "/graduation/generate",
    method: "GET",
  });
}

export function getAllDepartments() {
  return request({
    url: API_BASE_URL + "/department/list",
    method: "GET",
  });
}

export function getAllCourses() {
  return request({
    url: API_BASE_URL + "/course/list",
    method: "GET",
  });
}
export function graduationList() {
  return request({
    url: API_BASE_URL + "/graduation/list",
    method: "GET",
  });
}
