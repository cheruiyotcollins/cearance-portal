import React, { Component } from "react";
import {
  getAllPolls,
  
  studentLibraryClearance,
} from "../util/APIUtils";
import "./LibraryClearance.css";

import {  notification } from "antd";

class LibraryClearance extends Component {
  constructor(props) {
    super(props);

    this.state = {
      graduationData: [],
    };
    this.addLoan = this.addLoan.bind(this);
    this.clearStudent = this.clearStudent.bind(this);
  }

  clearStudent(id) {
       studentLibraryClearance(id)
      .then((response) => {
        if(response.status==="Success"){
          notification.success({
            message: response.status,
            description: response.description,
          });
        }else{
          notification.error({
            message: response.status,
            description: response.description,
          });
        }
      
        this.props.history.push(`/library/clearanced`);
        this.props.history.push(`/library/clearance`);
      })
      .catch((e) => {
          notification.error({
        
          message: "Failed",
          description: "Something went wrong",
        });
        this.props.history.push(`/library/clearanced`);
        this.props.history.push(`/library/clearance`);
      });
  }

  componentDidMount() {
    let promise;

    promise = getAllPolls();
    promise.then((response) => {
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
          Graduation- Library Clearance List Is Empty{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Graduation: Library Clearance</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Library Clearance</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.graduationData.map((graduation) => (
              <tr key={graduation.id}>
                <td> {graduation.id} </td>
                <td> {graduation.studentName} </td>
                <td> {graduation.regNo}</td>
                <td> {graduation.libraryClearance}</td>
                <td>
                  {" "}
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.clearStudent(graduation.id)}
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

export default LibraryClearance;
