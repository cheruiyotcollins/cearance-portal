import React, {  Component } from "react";
 import { getAllFinancialRecords } from '../util/APIUtils';
import "./FinancialRecords.css"


class FinancialRecords extends Component {
  constructor(props) {
    super(props);

    this.state = {
      financialData: [],
    };
    this.addLoan = this.addLoan.bind(this);
  }

  viewLoan(id) {
    this.props.history.push(`/view-loan/${id}`);
  }

 
    componentDidMount() {
      let promise;
      
      promise=getAllFinancialRecords();
      promise.then(response => {
        console.log(response);
         this.setState({
          financialData: response,
          
         
        })
    })
    
    
    
  }

  addLoan() {
    this.props.history.push("/add-loan/_add");
  }

  render() {
    if (this.state.financialData.length === 0)
    return (
      <h4
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {" "}
        No records found!!{" "}
      </h4>
    );

  return (
    <div style={{ marginTop: "10px"}}>
      <h2 className="styled-heading">Financial Records</h2>
       <table className="styled-table">
       <thead>
        <tr>
          <th>Id</th>
          <th>Student Name</th>
          <th>Registration No</th>
          <th>Outstading Fee</th>
          <th>Outstading Graduation Fee</th>
      
        
         </tr>
      </thead>
       <tbody>
        {this.state.financialData.map((graduation) => (
          <tr key={graduation.id}>
            <td> {graduation.id} </td>
            <td> {graduation.name} </td>
            <td> {graduation.regNo}</td>
            <td> {graduation.outstandingFee}</td>
            <td> {graduation.graduationFee}</td>
             {/* <td> {dateFormat(graduation.disbusedOnDate, "mmmm dS, yyyy, h:MM:ss TT")}</td>
             <td> {dateFormat(graduation.loanDueDate, "mmmm dS, yyyy")}</td> */}
            
           
          </tr>
        ))}
      </tbody>
          </table>

      </div>
  );
  }

  
}

export default FinancialRecords;