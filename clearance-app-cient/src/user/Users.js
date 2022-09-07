import React, { Component } from "react";

import { getUsers ,deleteUser} from "../util/APIUtils";

import "./LibraryRecords.css";
import { notification } from "antd";


class Users extends Component {
  constructor(props) {
    super(props);

    this.state = {
      userData: [],
    };
  
  }

  deleteUserById(id) {
    deleteUser(id)
      .then((response) => {
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

        this.props.history.push(`/library/clearanced`);
        this.props.history.push(`/user/list`);
      })
      .catch((e) => {
        notification.error({
          message: "Failed",
          description: "Something went wrong",
        });
        this.props.history.push(`/library/clearanced`);
        this.props.history.push(`/user/list`);
      });
  }

  componentDidMount() {
    let promise;

    promise = getUsers();
    promise.then((response) => {
      console.log(response);
      this.setState({
        userData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.userData.length === 0)
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
              <th>Name</th>
              <th>Username</th>
              <th>Email</th>
              <th>Role Name</th>
            
              <th> Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.userData.map((user) => (
              <tr key={user.id}>
                <td> {user.id} </td>
                <td> {user.name} </td>
                <td> {user.username}</td>
                <td> {user.email}</td>
                <td> {user.roleId}</td>
                <td>
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.deleteUserById(user.id)}
                    className="button-10"
                  >
                    Delete{" "}
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

export default Users;
