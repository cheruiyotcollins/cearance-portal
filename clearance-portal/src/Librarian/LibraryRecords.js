import React, { Component } from "react";
import dateFormat from "dateformat";
// import Apis from "../util/Apis";
import { getLibraryRecords } from "../util/APIUtils";
import "./LibraryRecords.css";

class LibraryRecords extends Component {
  constructor(props) {
    super(props);

    this.state = {
      libraryData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
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
          Graduation list is empty{" "}
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
              <th> Status</th>
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
                {/* <td> {dateFormat(library.expectedReturnDate, "mmmm dS, yyyy")}</td> */}
                <td> {library.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default LibraryRecords;
