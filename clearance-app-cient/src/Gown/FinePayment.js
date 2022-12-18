import React, { Component } from "react";
import { checkStudentRegistrationStatus, gownFinePayment } from "../util/APIUtils";
import "./Payment.css";
import { Link } from "react-router-dom";

import {
  NAME_MIN_LENGTH,
  NAME_MAX_LENGTH,
  USERNAME_MIN_LENGTH,
  USERNAME_MAX_LENGTH,
} from "../constants";

import { Form, Input, Button, notification } from "antd";

const FormItem = Form.Item;

class FinePayment extends Component {
  constructor(props) {
    super(props);
    this.state = {
      amount: {
        value: "",
      },
      transactionReference: {
        value: "",
      },
      regNo: {
        value: "",
      },
      
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.checkStudentRegistrationStatus =
      this.checkStudentRegistrationStatus.bind(this);
    this.handleDropdownChange = this.handleDropdownChange.bind(this);
  }

  handleDropdownChange(f) {
    this.setState({ paymentFor: f.target.value });
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

    const payment = {
      regNo: this.state.regNo.value,
      transactionReference: this.state.transactionReference.value,
      amount: this.state.amount.value,
    
    };
    console.log(payment);
    gownFinePayment(payment)
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
       
        this.props.history.push("/graduation/gown/clearance");
      })
      .catch((error) => {
        notification.error({
          message: "Records Portal",
          description:
            error.message || "Sorry! Something went wrong. Please try again!",
        });
      });
  }

  render() {
    return (
      <div className="signup-container">
        <h1 className="styled-title">Fee Payment</h1>
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
            <FormItem
              label="Transaction Amount"
              hasFeedback
              help={this.state.amount.errorMsg}
            >
              <Input
                size="large"
                name="amount"
                autoComplete="off"
                placeholder="Enter amount"
                value={this.state.amount.value}
                onChange={(event) => this.handleInputChange(event)}
              />
            </FormItem>
            <FormItem
              label=" Transaction Reference"
              hasFeedback
              validateStatus={this.state.transactionReference.validateStatus}
              help={this.state.transactionReference.errorMsg}
            >
              <Input
                size="large"
                name="transactionReference"
                autoComplete="off"
                placeholder="Enter Transaction Number"
                value={this.state.transactionReference.value}
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
                Pay{" "}
              </Button>
              Already Paid?{" "}
              <Link to="/student/list">Go back to gown clearance!</Link>
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

export default FinePayment;
