import React, { Component } from "react";
import { getStudentClearedForGraduation } from "../util/APIUtils";
import "./GownIssuance.css";

class StudentCleared extends Component {
  constructor(props) {
    super(props);

    this.state = {
      gownIssuanceData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

  componentDidMount() {
    let promise;

    promise = getStudentClearedForGraduation();
    promise.then((response) => {
      console.log(response);
      this.setState({
        gownIssuanceData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.gownIssuanceData.length === 0)
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
          Graduation: No Student Cleared{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">
          Graduation: Student Cleared For Graduation{" "}
        </h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Registration No</th>
              <th>Student Name</th>
            </tr>
          </thead>

          <tbody>
            {this.state.gownIssuanceData.map((issuance) => (
              <tr key={issuance.regNo}>
                <td> {issuance.regNo}</td>
                <td> {issuance.studentName} </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default StudentCleared;
