import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import "./AppHeader.css";
import { Layout, Menu, Dropdown, Icon } from "antd";
import { generateGraduationList } from "../util/APIUtils";
import Logo from "./logo.PNG";
const Header = Layout.Header;
class AppHeader extends Component {
  constructor(props) {
    super(props);
    this.handleMenuClick = this.handleMenuClick.bind(this);
    this.generate = this.generate.bind(this);
  }

  handleMenuClick({ key }) {
    if (key === "logout") {
      this.props.onLogout();
    }
  }

  generate() {
    generateGraduationList().then((response) => {
      this.props.history.push(`/`);
    });
  }

  render() {
    let menuItems;
    let appTitleItems;
    console.log(this.props.currentUser);
    if (this.props.currentUser) {
      if (this.props.currentUser.roleId === 1) {
        menuItems = [
          <Menu.Item key="/profile" className="profile-menu">
            <UsersDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,

          <Menu.Item key="/admin/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp; ADMIN PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 2) {
        menuItems = [
          <Menu.Item key="/profile" className="profile-menu">
            <StudentRecordsDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <StudentFinanceDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <StudentLibraryDropDown
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <StudentRegistrarDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <StudentReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp;STUDENT PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 3) {
        menuItems = [
          <Menu.Item key="/departments">
            <Link to="/departments">
              <h4 className="dropdown-display">Departments</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/courses">
            <Link to="/courses">
              <h4 className="dropdown-display">Courses</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/graduation/list">
            <Link to="/graduation/list">
              <h4 className="dropdown-display">Graduation List</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/hod/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp;HOD PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 4) {
        menuItems = [
          <Menu.Item key="/library/book/add">
            <Link to="/library/book/add">
              <h4 className="dropdown-display">Add Book</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/library/book/list">
            <Link to="/library/book/list">
              <h4 className="dropdown-display">Books</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/library/book/issue">
            <Link to="/library/book/issue">
              <h4 className="dropdown-display">Issue Book</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/library/list">
            <Link to="/library/list">
              <h4 className="dropdown-display">Issued Books</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/library/clearance">
            <Link to="/library/clearance">
              <h4 className="dropdown-display">Student Clearance</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/library/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp;LIBRARY PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 5) {
        menuItems = [
          <Menu.Item key="/finance/reports" className="profile-menu">
            <FinanceDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/finance/list">
            <Link to="/finance/list">
              <h4 className="dropdown-display">Balances</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/finance/clearance">
            <Link to="/finance/clearance">
              <h4 className="dropdown-display">Student Clearance</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/finance/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp;FINANCE PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 6) {
        menuItems = [
          <Menu.Item key="/graduation/fine/payment">
            <Link to="/graduation/fine/payment">
              <h4 className="dropdown-display">Fine Payment</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/graduation/certificate/issuance">
            <Link to="/graduation/certificate/issuance">
              <h4 className="dropdown-display">Certificate Issuance</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/graduation/gown/clearance">
            <Link to="/graduation/gown/clearance">
              <h4 className="dropdown-display">Gown clearance</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/graduation/gown/issuance">
            <Link to="/graduation/gown/issuance">
              <h4 className="dropdown-display">Gown Issuance</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/records/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title-display">
            <h1 className="app-title-display">&nbsp;RECORDS PORTAL</h1>
          </div>,
        ];
      } else if (this.props.currentUser.roleId === 7) {
        menuItems = [
          <Menu.Item key="/library/book/list">
            <Link to="/courses">
              <h4 className="dropdown-display">Courses</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/homeadmin">
            <Link to="/departments">
              <h4 className="dropdown-display">Departments</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/student/new">
            <Link to="/student/new">
              <h4 className="dropdown-display">Register Student</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/student/list">
            <Link to="/student/list">
              <h4 className="dropdown-display">Students</h4>
            </Link>
          </Menu.Item>,
          <Menu.Item key="/registrar/reports" className="profile-menu">
            <ReportsDropDownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
          <Menu.Item key="/profile" className="profile-menu">
            <ProfileDropdownMenu
              currentUser={this.props.currentUser}
              handleMenuClick={this.handleMenuClick}
            />
          </Menu.Item>,
        ];
        appTitleItems = [
          <div className="app-title">
            <h1 className="app-title-display">&nbsp;REGISTRAR PORTAL</h1>
          </div>,
        ];
      }
    } else {
      menuItems = [
        <Menu.Item key="/login">
          <Link to="/login">Login</Link>
        </Menu.Item>,
        <Menu.Item key="/signup">
          <Link to="/signup">Signup</Link>
        </Menu.Item>,
      ];
    }

    return (
      <Header className="app-header">
        <div className="container">
          <div className="app-title">
            <img
              src={Logo}
              alt="Zetech ERP"
              className="logo"
              width="110"
              length="200"
            />
          </div>
          <div className="app-title">{appTitleItems}</div>

          <Menu
            className="app-menu"
            mode="horizontal"
            selectedKeys={[this.props.location.pathname]}
            style={{ lineHeight: "64px" }}
          >
            {menuItems}
          </Menu>
        </div>
      </Header>
    );
  }
}

function ProfileDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Item key="user-info" className="dropdown-item" disabled>
        <div className="user-full-name-info">{props.currentUser.name}</div>
        <div className="username-info">@{props.currentUser.username}</div>
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="profile" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Profile</Link>
      </Menu.Item>
      <Menu.Item key="logout" className="dropdown-item">
        Logout
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <Icon type="user" className="nav-icon" style={{ marginRight: 0 }} />{" "}
        <Icon type="down" />
      </a>
    </Dropdown>
  );
}

function ReportsDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Item key="issuedGowns" className="dropdown-item">
        <Link to={`/graduation/gown/issued`}>Issued Gowns</Link>
      </Menu.Item>
      <Menu.Item key="clearedGowns" className="dropdown-item">
        <Link to={`/graduation/gown/cleared`}>Cleared Gowns</Link>
      </Menu.Item>

      <Menu.Divider />
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/graduation/certificate/issued`}>
          Degree/Diploma Certificates issued
        </Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/graduation/student/cleared`}>
          Students cleared for graduation
        </Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Reports</h4>
      </a>
    </Dropdown>
  );
}
//stated here
function LibraryDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/library/clearance`}>Student Clearance</Link>
      </Menu.Item>
      <Menu.Item key="issueBook" className="dropdown-item">
        <Link to={`/library/book/issue`}>Issue Book</Link>
      </Menu.Item>
      <Menu.Item key="addBook" className="dropdown-item">
        <Link to={`/library/book/add`}>Add Book</Link>
      </Menu.Item>
      <Menu.Item key="listBook" className="dropdown-item">
        <Link to={`/library/book/list`}>Books</Link>
      </Menu.Item>
      <Menu.Item key="listBook" className="dropdown-item">
        <Link to={`/library/list`}>Issued Books</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Library</h4>
      </a>
    </Dropdown>
  );
}
// second
function FinanceDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/finance/payment/list`}>Payments</Link>
      </Menu.Item>
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/finance/payment/add`}>Make Payments</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Payments</h4>
      </a>
    </Dropdown>
  );
}
// third
function StudentDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Edit Name</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Student</h4>
      </a>
    </Dropdown>
  );
}
// fouth
function HodDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/`}>Graduation List</Link>
      </Menu.Item>
      <Menu.Item
        key="Library"
        className="dropdown-item"
        onClick={() => this.generate()}
      >
        <h4>Generate Graduation List </h4>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">HOD</h4>
      </a>
    </Dropdown>
  );
}
function RecordsDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/graduation/gown/issuance`}>Gown Issuance</Link>
      </Menu.Item>

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/graduation/gown/clearance`}>Gown Clearance</Link>
      </Menu.Item>

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/graduation/certificate/issuance`}>
          Certificate Issuance
        </Link>
      </Menu.Item>

      {/* <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/graduation/gown/clearance`}>Gown Clearance</Link>
      </Menu.Item> */}
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Records</h4>
      </a>
    </Dropdown>
  );
}

function StudentRegistrarDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/student/name/confimation`}>
          Graduation Name Confirmation
        </Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Registrar</h4>
      </a>
    </Dropdown>
  );
}
function StudentFinanceDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item key="/finance/student/fee/balance" className="dropdown-item">
        <Link to={`/finance/student/fee/balance`}>Fee Balance</Link>
      </Menu.Item>

      <Menu.Item key="/finance/student/payments" className="dropdown-item">
        <Link to={"/finance/student/payments"}>Payments</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Finance</h4>
      </a>
    </Dropdown>
  );
}
function StudentLibraryDropDown(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/library/student/book/issued`}>Issued Books</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Library</h4>
      </a>
    </Dropdown>
  );
}
function StudentRecordsDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item
        key="student/gown/issuance/confimation"
        className="dropdown-item"
      >
        <Link to={`/student/gown/issuance/confimation`}>
          Confirm Gown Issuance{" "}
        </Link>
      </Menu.Item>

      <Menu.Item
        key="student/gown/clearance/confimation"
        className="dropdown-item"
      >
        <Link to={`/student/gown/clearance/confimation`}>
          Confirm Gown Clearance
        </Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Records</h4>
      </a>
    </Dropdown>
  );
}
function StudentReportsDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />

      <Menu.Item key="student/reports" className="dropdown-item">
        <Link to={`/student/reports`}>Graduation Clearance Status</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Reports</h4>
      </a>
    </Dropdown>
  );
}

function UsersDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/user/list`}>Users</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/user/new`}>New User</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display">Users</h4>
      </a>
    </Dropdown>
  );
}

function RegistrarDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/student/list`}>Students</Link>
      </Menu.Item>
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/student/new`}>New Student</Link>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <h4 className="dropdown-display"> Registrar </h4>
      </a>
    </Dropdown>
  );
}

export default withRouter(AppHeader);
