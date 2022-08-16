import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import "./AppHeader.css";
import { Layout, Menu, Dropdown, Icon } from "antd";
import { generateGraduationList } from "../util/APIUtils";
import Logo from "../logo.PNG";
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
    console.log(this.props.currentUser);
    if (this.props.currentUser) {
      menuItems = [
        <Menu.Item key="/profile" className="profile-menu">
        <RecordsDropDownMenu
          currentUser={this.props.currentUser}
          handleMenuClick={this.handleMenuClick}
        />
      </Menu.Item>,
       <Menu.Item key="/profile" className="profile-menu">
       <RegistrarDropDownMenu
         currentUser={this.props.currentUser}
         handleMenuClick={this.handleMenuClick}
       />
     </Menu.Item>,
             <Menu.Item key="/profile" className="profile-menu">
          <HodDropDownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,

        <Menu.Item key="/profile" className="profile-menu">
          <StudentDropDownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,
        <Menu.Item key="/profile" className="profile-menu">
          <FinanceDropDownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,

        <Menu.Item key="/profile" className="profile-menu">
          <LibraryDropDownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,

        <Menu.Item key="/profile" className="profile-menu">
          <ReportsDropDownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,
        // <Menu.Item key="/profile" className="profile-menu">
        //   <UsersDropDownMenu
        //     currentUser={this.props.currentUser}
        //     handleMenuClick={this.handleMenuClick}
        //   />
        // </Menu.Item>,

        <Menu.Item key="/profile" className="profile-menu">
          <ProfileDropdownMenu
            currentUser={this.props.currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,
      ];
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
            <img src={Logo} alt="Zetech ERP" className="logo" />
          </div>
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
      <Menu.Divider />
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Library Reports</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>
          Financial Reports
        </Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>
          Graduation Reports
        </Link>
      </Menu.Item>

      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Gown Issuance</Link>
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
        <Link to={`/finance/clearance`}>Finance Clearance</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/finance/list`}>Outstanding Balances</Link>
      </Menu.Item>
      <Menu.Item key="Library" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Fee Payment</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/finance/list`}>Payments Records</Link>
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
      <Menu.Item
        key="Library"
        className="dropdown-item"
        onClick={() => this.generate()}
      >
        <h4>Generate Graduation List </h4>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/`}>View Graduation List</Link>
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
        <Link to={`/graduation/gown/clearance`}>Certificate Issuance</Link>
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

function UsersDropDownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Divider />
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>All</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Admins</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Librarians</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Hods</Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>
          Finance Officers
        </Link>
      </Menu.Item>
      <Menu.Item key="Finance" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Users</Link>
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
