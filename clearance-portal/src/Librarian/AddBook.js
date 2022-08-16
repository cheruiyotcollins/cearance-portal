import React, { Component } from "react";
import {
  checkStudentRegistrationStatus,
  addBook,
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
      bookName: {
        value: "",
      },
      author: {
        value: "",
      },
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.checkStudentRegistrationStatus =
      this.checkStudentRegistrationStatus.bind(this);
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
      bookName: this.state.bookName.value,
      author: this.state.author.value,
    };
    console.log(book);
    addBook(book)
      .then((response) => {
        notification.success({
          message: "Library Portal",
          description: "Added Book successfully",
        });
        this.props.history.push("/library/list");
      })
      .catch((error) => {
        notification.error({
          message: "Library Portal",
          description:
            error.message || "Sorry! Something went wrong. Please try again!",
        });
      });
  }

  render() {
    return (
      <div className="signup-container">
        <h1 className="styled-title">New Book</h1>
        <div className="signup-content">
          <Form onSubmit={this.handleSubmit} className="signup-form">
            <FormItem
              label="Name"
              hasFeedback
              validateStatus={this.state.bookName.validateStatus}
              help={this.state.bookName.errorMsg}
            >
              <Input
                size="large"
                name="bookName"
                autoComplete="off"
                placeholder="Enter Name Here"
                value={this.state.bookName.value}
                onChange={(event) => this.handleInputChange(event)}
              />
            </FormItem>
            <FormItem
              label="Author"
              hasFeedback
              validateStatus={this.state.author.validateStatus}
              help={this.state.author.errorMsg}
            >
              <Input
                size="large"
                name="author"
                autoComplete="off"
                placeholder="Enter the author of the book"
                value={this.state.author.value}
                onChange={(event) => this.handleInputChange(event)}
              />
            </FormItem>
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
