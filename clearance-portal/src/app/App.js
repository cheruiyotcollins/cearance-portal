import React, { Component } from "react";
import "./App.css";
import { Route, withRouter, Switch } from "react-router-dom";

import { getCurrentUser } from "../util/APIUtils";
import { ACCESS_TOKEN } from "../constants";
import PollList from "../poll/PollList";
import GraduationList from "../Hod/GraduationList";
import FinancialRecords from "../Finance/FinancialRecords";
import FinanceClearance from "../Finance/FinanceClearance";
import NewStudent from "../Student/NewStudent";
import StudentList from "../Student/StudentList";
import LibraryRecords from "../Librarian/LibraryRecords";
import LibraryClearance from "../Librarian/LibraryClearance";
import IssueBook from "../Librarian/IssueBook";
import BookList from "../Librarian/BookList";
import AddBook from "../Librarian/AddBook";
import GownClearance from "../Gown/GownClearance";
import GownIssuance from "../Gown/GownIssuance";
import NewPoll from "../poll/NewPoll";
import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import Profile from "../user/profile/Profile";
import AppHeader from "../common/AppHeader";
import NotFound from "../common/NotFound";
import LoadingIndicator from "../common/LoadingIndicator";
import PrivateRoute from "../common/PrivateRoute";

import { Layout, notification } from "antd";
const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: true,
    };
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: "topRight",
      top: 70,
      duration: 3,
    });
  }

  loadCurrentUser() {
    getCurrentUser()
      .then((response) => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false,
        });
      })
      .catch((error) => {
        this.setState({
          isLoading: false,
        });
      });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(
    redirectTo = "/",
    notificationType = "success",
    description = "You're successfully logged out."
  ) {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false,
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: "Clearance Portal",
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: "Clearance Portal",
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />;
    }

    return (
      <Layout className="app-container">
        <AppHeader
          isAuthenticated={this.state.isAuthenticated}
          currentUser={this.state.currentUser}
          onLogout={this.handleLogout}
        />

        <Content className="app-content">
          <div className="container">
            <Switch>
              <Route
                exact
                path="/list"
                render={(props) => (
                  <PollList
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/"
                render={(props) => (
                  <GraduationList
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/student/list"
                render={(props) => (
                  <StudentList
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/student/new"
                render={(props) => (
                  <NewStudent
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/finance/list"
                render={(props) => (
                  <FinancialRecords
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/library/list"
                render={(props) => (
                  <LibraryRecords
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/library/clearance"
                render={(props) => (
                  <LibraryClearance
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>

              <Route
                exact
                path="/library/book/add"
                render={(props) => (
                  <AddBook
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/library/book/list"
                render={(props) => (
                  <BookList
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
               <Route
                exact
                path="/finance/clearance"
                render={(props) => (
                  <FinanceClearance
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>

              <Route
                exact
                path="/library/book/issue"
                render={(props) => (
                  <IssueBook
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/graduation/gown/issuance"
                render={(props) => (
                  <GownIssuance
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/graduation/gown/clearance"
                render={(props) => (
                  <GownClearance
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                exact
                path="/student/new"
                render={(props) => (
                  <AddBook
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    handleLogout={this.handleLogout}
                    {...props}
                  />
                )}
              ></Route>
              <Route
                path="/login"
                render={(props) => (
                  <Login onLogin={this.handleLogin} {...props} />
                )}
              ></Route>
              <Route path="/signup" component={Signup}></Route>
              <Route
                path="/users/:username"
                render={(props) => (
                  <Profile
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    {...props}
                  />
                )}
              ></Route>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                path="/poll/new"
                component={NewPoll}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <Route component={NotFound}></Route>
            </Switch>
          </div>
        </Content>
      </Layout>
    );
  }
}

export default withRouter(App);
