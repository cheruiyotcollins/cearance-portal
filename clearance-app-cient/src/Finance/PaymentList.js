import React, { Component } from "react";
import { getAllPayments } from "../util/APIUtils";
import "./PaymentList.css";
import dateFormat from "dateformat";

class PaymentList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      paymentData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

  componentDidMount() {
    let promise;

    promise = getAllPayments();
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
          Finance: No Payment Found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Finance: Payment Records</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Amount</th>
              <th>Transaction Reference</th>
              <th> Paid On</th>
            </tr>
          </thead>
          ;
          <tbody>
            {this.state.paymentData.map((payment) => (
              <tr key={payment.id}>
                <td> {payment.id} </td>
                <td> {payment.studentName} </td>
                <td> {payment.regNo}</td>
                <td> {payment.amount}</td>
                <td> {payment.transactionReference}</td>
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

export default PaymentList;
