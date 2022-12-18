import React, { Component } from "react";
import { getAllGownsIssued } from "../util/APIUtils";
import "./GownIssuance.css";
import dateFormat from "dateformat";

class IssuedGowns extends Component {
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

    promise = getAllGownsIssued();
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
          Graduation: No Issued Gown{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Graduation: Students Issued Gowns </h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Issued On</th>
              <th>Return By</th>
            </tr>
          </thead>

          <tbody>
            {this.state.gownIssuanceData.map((issuance) => (
              <tr key={issuance.regNo}>
                <td> {issuance.studentName} </td>
                <td> {issuance.regNo}</td>
                <td>
                  {dateFormat(issuance.IssuedDate, "mmmm dS, yyyy, h:MM:ss TT")}
                </td>
                <td>
                  {dateFormat(issuance.returnDate, "mmmm dS, yyyy, h:MM:ss TT")}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default IssuedGowns;
