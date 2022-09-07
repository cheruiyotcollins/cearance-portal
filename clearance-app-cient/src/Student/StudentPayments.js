import React, { Component } from "react";
import dateFormat from "dateformat";
import { getStudentPayments } from "../util/APIUtils";
import "./LibraryRecords.css";
import { notification } from "antd";

class StudentPayments extends Component {
  constructor(props) {
    super(props);

    this.state = {
      paymentData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  componentDidMount() {
    let promise;

    promise = getStudentPayments(this.props.currentUser.id);
    promise.then((response) => {
      console.log(response);
      this.setState({
        paymentData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.paymentData.length === 0)
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
          Library: No Issuance Record Found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Library Book Issuance Records</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Amount</th>
              <th>Transaction Id</th>
              <th>Paid On</th>
              </tr>
          </thead>
          <tbody>
          {this.state.paymentData.map((payment) => (
              <tr key={payment.id}>
                <td> {payment.id} </td>
                <td> {payment.amount} </td>
                <td> {payment.transactionId}</td>
                <td>
                  {" "}
                  {dateFormat(payment.paidAt, "mmmm dS, yyyy, h:MM:ss TT")}
                </td>
              
              </tr>
              ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default StudentPayments;
