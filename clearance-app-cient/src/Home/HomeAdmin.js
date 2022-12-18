import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import "./HomeAdmin.css";

class HomeAdmin extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="backgroundImage">
        <br></br>
        <br></br>
        <div className="main-home">
          <br></br>
          <span className="title">
            <h3>Welcome To Gigster Graduation Clearance ERP</h3>{" "}
          </span>

          <span className="home">
            {" "}
            New user? Please Signup Here <a href="/signup">Signup!</a>
            <br></br>
            Already have an account Login to access our services{" "}
            <a href="/login">Login!</a>
          </span>
        </div>
      </div>
    );
  }
}

export default withRouter(HomeAdmin);
