import React, { Component } from "react";
import { getAllPolls, gownIssuance } from "../util/APIUtils";
import "./GownIssuance.css";

class GownIssuance extends Component {
  constructor(props) {
    super(props);

    this.state = {
      graduationData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  issueGown(id) {
    gownIssuance(id).then((response) => {
      console.log(response);
      this.props.history.push(`/view-loan/${id}`);
      this.props.history.push(`/graduation/gown/issuance`);
    });
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
        <h2 className="styled-heading">Graduation: Gown Issuance</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Finance Clearance</th>
              <th>Library Clearance</th>
              <th> Gown Issuance Status</th>
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
                <td>
                  {" "}
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.issueGown(graduation.id)}
                    className="button-10"
                  >
                    Clear{" "}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default GownIssuance;
