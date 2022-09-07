import React, { Component } from "react";
import { getAllStudents } from "../util/APIUtils";
import "./StudentList.css";

class StudentList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      studentData: [],
    };
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

  componentDidMount() {
    let promise;
    promise = getAllStudents();
    promise.then((response) => {
      console.log(response);
      this.setState({
        studentData: response,
      });
    });
  }

  render() {
    if (this.state.studentData.length === 0)
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
          Student list is empty{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Students</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Course</th>
              <th>Department</th>
            </tr>
          </thead>
          <tbody>
            {this.state.studentData.map((student) => (
              <tr key={student.id}>
                <td> {student.id} </td>
                <td> {student.name} </td>
                <td> {student.regNo}</td>
                <td> {student.course}</td>
                <td> {student.department}</td>

                {/* <td> {dateFormat(graduation.disbusedOnDate, "mmmm dS, yyyy, h:MM:ss TT")}</td>
             <td> {dateFormat(graduation.loanDueDate, "mmmm dS, yyyy")}</td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default StudentList;
