import React, { Component } from "react";
import {
  checkStudentRegistrationStatus,
  getAllBooks,
  issueBook,
} from "../util/APIUtils";
import "./IssueBook.css";
import { Link } from "react-router-dom";

import {
  NAME_MIN_LENGTH,
  NAME_MAX_LENGTH,
  USERNAME_MIN_LENGTH,
  USERNAME_MAX_LENGTH,
} from "../constants";

import { Form, Input, Button, notification } from "antd";

const FormItem = Form.Item;

class IssueBook extends Component {
  constructor(props) {
    super(props);
    this.state = {
      regNo: {
        value: "",
      },
      bookId: 0,
      duration: "",

      bookData: [],
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDropdownChange = this.handleDropdownChange.bind(this);
    this.handleDropdownChangeDuration =
      this.handleDropdownChangeDuration.bind(this);
    this.checkStudentRegistrationStatus =
      this.checkStudentRegistrationStatus.bind(this);
  }
  componentDidMount() {
    getAllBooks().then((response) => {
      this.setState({
        bookData: response,
      });
    });
  }

  handleDropdownChange(f) {
    this.setState({ bookId: f.target.value });
  }
  handleDropdownChangeDuration(f) {
    this.setState({ duration: f.target.value });
  }

  handleInputChange(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value;

    this.setState({
      [inputName]: {
        value: inputValue,
      },
    });
    console.log(inputName);
  }

  handleSubmit(event) {
    event.preventDefault();

    const book = {
      regNo: this.state.regNo.value,
      bookId: this.state.bookId,
      duration: this.state.duration,
    };
    console.log(book);
    issueBook(book)
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

        this.props.history.push("/library/list");
      })
      .catch((error) => {
        notification.error({
          message: "Library Book Issuance",
          description: "Something went wrong",
        });
        this.props.history.push("/library/list");
      });
  }

  render() {
    return (
      <div className="signup-container">
        <h1 className="styled-title">Library Book Issuance</h1>
        <div className="signup-content">
          <Form onSubmit={this.handleSubmit} className="signup-form">
            <FormItem
              label=" Student Registration Number"
              hasFeedback
              validateStatus={this.state.regNo.validateStatus}
              help={this.state.regNo.errorMsg}
            >
              <Input
                size="large"
                name="regNo"
                autoComplete="off"
                placeholder="Enter Registration Number"
                value={this.state.regNo.value}
                onBlur={this.checkStudentRegistrationStatus}
                onChange={(event) => this.handleInputChange(event)}
              />
            </FormItem>
            <div>
              <form className="signup-form">
                <label>
                  Book:<br></br>
                  <select id="dropdown" onChange={this.handleDropdownChange}>
                    {" "}
                    <option value="1">Please Select</option>
                    {this.state.bookData.map((book) => (
                      <option value={book.id}>{book.bookName}</option>
                    ))}
                  </select>
                </label>
              </form>
            </div>
            <br></br>
            <div>
              <form className="signup-form">
                <label>
                  Duration:<br></br>
                  <select
                    id="dropdown"
                    onChange={this.handleDropdownChangeDuration}
                  >
                    <option value="1">Please Select</option>
                    <option value="1">1 Day</option>
                    <option value="7">1 Week</option>
                    <option value="14">2 Weeks</option>
                    <option value="30">1 Month</option>
                  </select>
                </label>
              </form>
            </div>

            <FormItem>
              <Button
                type="primary"
                htmlType="submit"
                size="large"
                className="signup-form-button"
              >
                Save{" "}
              </Button>
              Already registed?{" "}
              <Link to="/student/list">Go back to library records!</Link>
            </FormItem>
          </Form>
        </div>
      </div>
    );
  }

  // Validation Functions
  validateRegNo = (reg) => {
    if (reg.length < USERNAME_MIN_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Registration No is too short (Minimum  characters needed.)`,
      };
    } else if (reg.length > USERNAME_MAX_LENGTH) {
      return {
        validationStatus: "error",
        errorMsg: `Registration No is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`,
      };
    } else {
      return {
        validateStatus: null,
        errorMsg: null,
      };
    }
  };

  checkStudentRegistrationStatus() {
    const reg = this.state.regNo.value;
    const regNoValidation = this.validateRegNo(reg);

    if (regNoValidation.validateStatus === "error") {
      this.setState({
        regNo: {
          value: reg,
          ...regNoValidation,
        },
      });
      return;
    }

    this.setState({
      regNo: {
        value: reg,
        validateStatus: "validating",
        errorMsg: null,
      },
    });

    checkStudentRegistrationStatus(reg)
      .then((response) => {
        if (response.available) {
          this.setState({
            regNo: {
              value: reg,
              validateStatus: "error",
              errorMsg: "Registration number does not exist in the database",
            },
          });
        } else {
          this.setState({
            regNo: {
              value: reg,
              validateStatus: "success",
              errorMsg: null,
            },
          });
        }
      })
      .catch((error) => {
        // Marking validateStatus as success, Form will be recchecked at server
        this.setState({
          regNo: {
            value: reg,
            validateStatus: "success",
            errorMsg: null,
          },
        });
      });
  }

  validateName = (name) => {
    if (name.length < NAME_MIN_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`,
      };
    } else if (name.length > NAME_MAX_LENGTH) {
      return {
        validationStatus: "error",
        errorMsg: `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };
}

export default IssueBook;
