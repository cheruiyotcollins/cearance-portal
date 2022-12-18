import React, { Component } from "react";
import { getAllCourses } from "../util/APIUtils";
import "./GraduationList.css";

class CoursesList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseData: [],
    };
   
  }

  componentDidMount() {
    getAllCourses().then((response) => {
      console.log(response);
      this.setState({
        courseData: response,
      });
    });
  }

  editCourses() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.courseData.length === 0)
      return (
        <h4
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          {" "}
          <br></br>
          Courses list is empty{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">HOD: Courses List</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Course Name</th>
              <th>Action</th>
            </tr>
          </thead>

         <tbody>
            {this.state.courseData.map((course) => (
              <tr key={course.id}>
                <td> {course.id} </td>
                <td> {course.courseName} </td>
                <td>
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.editDepartment(course.id)}
                    className="button-10"
                  >
                    Edit{" "}
                  </button>{" "}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default CoursesList;
