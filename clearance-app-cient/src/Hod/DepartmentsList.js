import React, { Component } from "react";
import { getAllDepartments } from "../util/APIUtils";
import "./GraduationList.css";

class DepartmentsList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      departmentData: [],
    };
   
  }

  componentDidMount() {
    getAllDepartments().then((response) => {
      console.log(response);
      this.setState({
        departmentData: response,
      });
    });
  }

  editDepartment() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.departmentData.length === 0)
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
          Department list is empty{" "}
        </h4>
      );

    return (
      <div style={{ marginTop: "10px" }}>
        <h2 className="styled-heading">HOD: Department List</h2>
        <table className="styled-table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Department Name</th>
              <th>Action</th>
            </tr>
          </thead>

         <tbody>
            {this.state.departmentData.map((department) => (
              <tr key={department.id}>
                <td> {department.id} </td>
                <td> {department.departmentName} </td>
                <td>
                  <button
                    style={{ marginRight: "5px" }}
                    onClick={() => this.editDepartment(department.id)}
                    className="button-10"
                  >
                    View{" "}
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

export default DepartmentsList;
