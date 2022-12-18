import React, { Component } from "react";
import dateFormat from "dateformat";
import { getFeeBalanceById } from "../util/APIUtils";
import "./LibraryRecords.css";
import { notification } from "antd";

class StudentFeeBalance extends Component {
  constructor(props) {
    super(props);

    this.state = {
      financeData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  componentDidMount() {
    let promise;

    promise = getFeeBalanceById(this.props.currentUser.id);
    promise.then((response) => {
      console.log(response);
      this.setState({
        financeData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.financeData.length === 0)
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
          Finance: Not Found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Fee Balance</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Student Fee Balance</th>
              <th></th>
              </tr>
          </thead>
          <tbody>
            <tr key={this.state.financeData.id}>
              <td> <h4>Student Name</h4> </td>
              <td> {this.state.financeData.name} </td>
            </tr>
            <tr key={this.state.financeData.id}>
              <td> <h4>Outstading Tuition Fee</h4></td>
              <td> {this.state.financeData.outstandingFee}</td>
              
            </tr>
            <tr key={this.state.financeData.id}>
              <td>  <h4>Graduation Fee</h4> </td>
              <td> {this.state.financeData.graduationFee}</td>
              
            </tr>
            <tr key={this.state.financeData.id}>
              <td> <h3>Total</h3> </td>
              <td> {this.state.financeData.total}</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default StudentFeeBalance;
