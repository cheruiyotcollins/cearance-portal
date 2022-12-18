import React, { Component } from "react";
import dateFormat from "dateformat";
import { studentGownClearance, studentGownClearanceStatus } from "../util/APIUtils";
import "./LibraryRecords.css";
import { notification } from "antd";

class GownClearanceConfirmation extends Component {
  constructor(props) {
    super(props);

    this.state = {
      gownData: [],
    };
    
  }

  componentDidMount() {
    let promise;

    promise = studentGownClearanceStatus(this.props.currentUser.id);
    promise.then((response) => {
      console.log(response);
      this.setState({
        gownData: response,
      });
    });
  }

   confirmClearance() {
    
    studentGownClearance(this.props.currentUser.id)
      .then((response) => {
        notification.success({
          message: "Student Portal",
          description: "gown cleared successfully",
        });
        this.props.history.push("/student/name/confidsdsdsmation");
        this.props.history.push("/student/gown/clearance/confimation");
      })
      .catch((error) => {
        notification.error({
          message: "Student Portal",
          description:
            error.message || "Sorry! Something went wrong. Please try again!",
        });
      });
  }

  render() {
    if (this.state.gownData.length === 0)
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
          Student: Not Found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Gown Clearance</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Student Name</th>
              <th>Gown Clearance Status</th>
               <th>Action</th>
            </tr>
          </thead>
      
          <tbody>
           
              <tr key={this.state.gownData.id}>
                <td> {this.state.gownData.id} </td>
                <td> {this.state.gownData.name} </td>
                <td> {this.state.gownData.confirmationStatus}</td>
                
                <td>
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.confirmClearance()}
                    className="button-10"
                  >
                    Confirm{" "}
                  </button>{" "}
                </td>
           
              </tr>
          
          </tbody>
        </table>
      </div>
    );
  }
}

export default GownClearanceConfirmation;
