import React, { Component } from "react";
import {
  editStudentName,
  getAllDepartments,
  getAllCourses,
  checkStudentRegistrationStatus,
} from "../util/APIUtils";
import "./LibraryRecords.css";
import { Link } from "react-router-dom";

import {
  NAME_MIN_LENGTH,
  NAME_MAX_LENGTH,
  USERNAME_MIN_LENGTH,
  USERNAME_MAX_LENGTH,
} from "../constants";

import { Form, Input, Button, notification } from "antd";

const FormItem = Form.Item;

class EditName extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: {
        value: "",
      },
      regNo: {
        value: "",
      },
      course: 0,

      departmentId: 0,

      departmentData: [],
      courseData: [],
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDropdownChange = this.handleDropdownChange.bind(this);
    this.checkStudentRegistrationStatus =
      this.checkStudentRegistrationStatus.bind(this);
    this.handleDropdownChangeCourse =
      this.handleDropdownChangeCourse.bind(this);
  }
  componentDidMount() {
    getAllDepartments().then((response) => {
      this.setState({
        departmentData: response,
      });
    });
    getAllCourses().then((response) => {
      this.setState({
        courseData: response,
      });
    });
  }

  handleDropdownChange(f) {
    this.setState({ departmentId: f.target.value });
  }

  handleDropdownChangeCourse(e) {
    this.setState({ course: e.target.value });
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

    const nameNId = {
      name: this.state.name.value,
      id: this.props.currentUser.id,
      
    };
    console.log(nameNId);
    editStudentName(nameNId)
      .then((response) => {
        notification.success({
          message: "Student Portal",
          description: "Name Confirmed successfully",
        });
        this.props.history.push("/student/name/confimation");
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
    return (
      <div className="signup-container">
        <h1 className="styled-title">Name Confirmation</h1>
        <div className="signup-content">
          <Form onSubmit={this.handleSubmit} className="signup-form">
            <FormItem
              label="Full Name"
              validateStatus={this.state.name.validateStatus}
              help={this.state.name.errorMsg}
            >
              <Input
                size="large"
                name="name"
                autoComplete="off"
                placeholder="Enter Full name"
                value={this.state.name.value}
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
                Confirm{" "}
              </Button>
              Already confirmed?{" "}
              <Link to="/student/name/confimation">Go back</Link>
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
              validateStatus: "success",
              errorMsg: null,
            },
          });
        } else {
          this.setState({
            regNo: {
              value: reg,
              validateStatus: "error",
              errorMsg:
                "Student with same registration number is already registrered",
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

export default EditName;
