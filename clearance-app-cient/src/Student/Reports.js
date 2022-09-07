import React, { Component } from "react";
import { getStudentReports } from "../util/APIUtils";
import { notification } from "antd";

class Reports extends Component {
  constructor(props) {
    super(props);

    this.state = {
      reportsData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  componentDidMount() {
    let promise;

    promise = getStudentReports(this.props.currentUser.id);
    promise.then((response) => {
      if (response.status === "Fail") {
        notification.error({
          message: response.status,
          description: response.description,
        });
      } else {
        this.setState({
          reportsData: response,
        });
        notification.success({
          message: response.status,
          description: response.description,
        });
      }

      
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.reportsData.length === 0)
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
          Student Portal: No  Record Found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Student Reports</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th> Registration No</th>
              <th> Library Clearance</th>
              <th> Finance Clearance</th>
              <th> Gown Issuance</th>
              <th> Gown Clearance</th>
              <th> Certificate Issuance</th>
            </tr>
          </thead>
          <tbody>
            <tr key={this.state.reportsData.id}>
              <td> {this.state.reportsData.id} </td>
              <td> {this.state.reportsData.studentName}</td>
              <td> {this.state.reportsData.regNo}</td>
              <td> {this.state.reportsData.libraryClearance}</td>
              <td> {this.state.reportsData.financeClearance}</td>
              <td> {this.state.reportsData.gownIssuance}</td>
              <td> {this.state.reportsData.gownClearance}</td>
              <td> {this.state.reportsData.certificateIssuance}</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default Reports;
