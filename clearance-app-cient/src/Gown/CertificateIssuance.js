import React, { Component } from "react";
import { getAllPolls, certificateIssuance } from "../util/APIUtils";
import "./GownIssuance.css";
import { notification } from "antd";
class CertificateIssuance extends Component {
  constructor(props) {
    super(props);

    this.state = {
      graduationData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  issueCertificate(id) {
    certificateIssuance(id)
      .then((response) => {
        console.log(response);
        if (response.status === "Success") {
          notification.success({
            message: response.status,
            description: response.description,
          });
        } else {
          notification.error({
            message: response.status,
            description: response.description,
          });
        }
        this.props.history.push(`/view-loan/${id}`);
        this.props.history.push(`/graduation/certificate/issuance`);
      })
      .catch((error) => {
        notification.error({
          message: "Records Certificate Issuance",
          description: "Something went wrong",
        });
        this.props.history.push(`/view-loan/${id}`);
        this.props.history.push(`/graduation/certificate/issuance`);
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
          Certificate: No certificate issued{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Records: Certificate Issuance</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Certificate Issuance</th>
              <th> Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.graduationData.map((graduation) => (
              <tr key={graduation.id}>
                <td> {graduation.id} </td>
                <td> {graduation.studentName} </td>
                <td> {graduation.regNo}</td>
                <td> {graduation.certificateIssuance}</td>
                <td>
                  {" "}
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.issueCertificate(graduation.id)}
                    className="button-10"
                  >
                    Issue{" "}
                  </button>{" "}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default CertificateIssuance;
