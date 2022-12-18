import React, { Component } from "react";
import { getAllPolls } from "../util/APIUtils";
import "./GraduationList.css";

class GraduationList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      graduationData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

  componentDidMount() {
    let promise;

    promise = getAllPolls();
    promise.then((response) => {
      console.log(response);
      this.setState({
        graduationData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.graduationData.length === 0)
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
          Graduation list is empty{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Graduation List</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Finance Clearance</th>
              <th>Library Clearance</th>
              <th>Gown Issuance Status</th>
              <th>Gown Clearance Status</th>
              <th>Certificate Issuance</th>
            </tr>
          </thead>
          <tbody>
            {this.state.graduationData.map((graduation) => (
              <tr key={graduation.id}>
                <td> {graduation.id} </td>
                <td> {graduation.studentName} </td>
                <td> {graduation.regNo}</td>
                <td> {graduation.financeClearance}</td>
                <td> {graduation.libraryClearance}</td>
                <td> {graduation.gownIssuance}</td>
                <td> {graduation.gownClearance}</td>
                <td> {graduation.certificateIssuance}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default GraduationList;
