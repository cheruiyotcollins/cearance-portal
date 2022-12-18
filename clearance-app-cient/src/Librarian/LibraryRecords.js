import React, { Component } from "react";
import dateFormat from "dateformat";
import { getLibraryRecords, studentBookClearance } from "../util/APIUtils";
import ConfirmDialog from "../util/ConfirmDialog";
import "./LibraryRecords.css";
import { notification } from "antd";
import { IconButton } from "@material-ui/core";
import { Input as InputIcon } from "@material-ui/icons";

class LibraryRecords extends Component {
  constructor(props) {
    super(props);

    this.state = {
      libraryData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  clearBook(id) {
    studentBookClearance(id)
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
        this.props.history.push(`/library/list`);
      })
      .catch((e) => {
        notification.error({
          message: "Failed",
          description: "Something went wrong",
        });
        this.props.history.push(`/library/clearanced`);
        this.props.history.push(`/library/list`);
      });
  }

  componentDidMount() {
    let promise;

    promise = getLibraryRecords();
    promise.then((response) => {
      console.log(response);
      this.setState({
        libraryData: response,
      });
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
              <th>Student Name</th>
              <th>Registration No</th>
              <th>Book Name</th>
              <th> Issued At</th>
              <th> Return By</th>
              <th> Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.libraryData.map((library) => (
              <tr key={library.id}>
                <td> {library.id} </td>
                <td> {library.studentName} </td>
                <td> {library.regNo}</td>
                <td> {library.bookName}</td>
                <td>
                  {" "}
                  {dateFormat(library.issuedDate, "mmmm dS, yyyy, h:MM:ss TT")}
                </td>
                <td>
                  {" "}
                  {dateFormat(
                    library.expectedReturnDate,
                    "mmmm dS, yyyy, h:MM:ss TT"
                  )}
                </td>
                <td>
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.clearBook(library.id)}
                    className="button-10"
                  >
                    Clear{" "}
                  </button>{" "}
                </td>
                {/* <td>
                  <div>
                    <IconButton
                      aria-label="delete"
                      onClick={() => setConfirmOpen(true)}
                    >
                      <InputIcon />
                    </IconButton>
                    <ConfirmDialog
                      title="Delete Post?"
                      open={confirmOpen}
                      setOpen={setConfirmOpen}
                      onConfirm={this.clearBook(library.id)}
                    >
                      Are you sure you want to delete this post?
                    </ConfirmDialog>
                  </div>
                </td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default LibraryRecords;
