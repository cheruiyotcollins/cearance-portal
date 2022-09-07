import React, { Component } from "react";
import dateFormat from "dateformat";
// import Apis from "../util/Apis";
import { getAllBooks } from "../util/APIUtils";
import "./BookList.css";

class BookList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      bookData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

  componentDidMount() {
    let promise;

    promise = getAllBooks();
    promise.then((response) => {
      console.log(response);
      this.setState({
        bookData: response,
      });
    });
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.bookData.length === 0)
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
          Library: no book found{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">Library Book Records</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Book Name</th>
              <th>Book Author</th>
            </tr>
          </thead>
          <tbody>
            {this.state.bookData.map((book) => (
              <tr key={book.id}>
                <td> {book.id} </td>
                <td> {book.bookName} </td>
                <td> {book.author}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default BookList;
