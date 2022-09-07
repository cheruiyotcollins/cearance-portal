import React, { Component } from "react";
import { getStudentById, editStudentName } from "../util/APIUtils";
import "./LibraryRecords.css";
import { notification } from "antd";

class Student extends Component {
  constructor(props) {
    super(props);

    this.state = {
      studentData: [],
    };
  }

  confirm() {
    

    const nameNId = {
      name: this.state.studentData.name,
      id: this.props.currentUser.id,
    };
    console.log(nameNId);
    editStudentName(nameNId)
      .then((response) => {
        notification.success({
          message: "Student Portal",
          description: "Name Confirmed successfully",
        });
        this.props.history.push("/student/name/confidsdsdsmation");
        this.props.history.push("/student/name/confimation");
      })
      .catch((error) => {
        notification.error({
          message: "Student Portal",
          description:
            error.message || "Sorry! Something went wrong. Please try again!",
        });
      });
  }
  editName(id) {
    this.props.history.push(`/student/name/confimation/form`);
  }

  componentDidMount() {
    let promise;
    promise = getStudentById(this.props.currentUser.id);
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
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Course</th>
              <th>Department</th>
              <th>Name Confirmation</th>
              <th>Confirm</th>
              <th>Edit</th>
            </tr>
          </thead>
        
          <tbody>
            <tr key={this.state.studentData.id}>
            
              <td> {this.state.studentData.name} </td>
              <td> {this.state.studentData.regNo}</td>
              <td> {this.state.studentData.course}</td>
              <td> {this.state.studentData.department}</td>
              <td> {this.state.studentData.nameConfirmation}</td>
              <td>
                <button
                  style={{ marginRight: "5px" }}
                  onClick={() => this.editName()}
                  className="button-10"
                >
                  Edit{" "}
                </button>{" "}
              </td>
              <td>
                <button
                  style={{ marginRight: "5px" }}
                  onClick={() => this.confirm()}
                  className="button-10"
                >
                  Confirm{" "}
                </button>{" "}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default Student;
