import React, { Component } from "react";
import dateFormat from "dateformat";
import { getBookById } from "../util/APIUtils";
import { notification } from "antd";

class IssuedBooks extends Component {
  constructor(props) {
    super(props);

    this.state = {
      libraryData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  componentDidMount() {
    let promise;

    promise = getBookById(this.props.currentUser.id);
    promise.then((response) => {
      if (response.status === "Fail") {
        notification.error({
          message: response.status,
          description: response.description,
        });
      } else {
        this.setState({
          libraryData: response,
        });
        notification.success({
          message: response.status,
          description: response.description,
        });
      }

      this.props.history.push(`/library/clearanced`);
      this.props.history.push(`/library/list`);
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.libraryData.length === 0)
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
              <th>Book Name</th>
              <th> Issued At</th>
              <th> Return By</th>
            </tr>
          </thead>
          <tbody>
            <tr key={this.state.libraryData.id}>
              <td> {this.state.libraryData.id} </td>
              <td> {this.state.libraryData.bookName}</td>
              <td>
                {" "}
                {dateFormat(
                  this.state.libraryData.issuedDate,
                  "mmmm dS, yyyy, h:MM:ss TT"
                )}
              </td>
              <td>
                {" "}
                {dateFormat(
                  this.state.libraryData.expectedReturnDate,
                  "mmmm dS, yyyy, h:MM:ss TT"
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default IssuedBooks;
