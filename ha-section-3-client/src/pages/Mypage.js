import React from 'react';
import axios from 'axios';

axios.defaults.withCredentials = true;

function Mypage({ userinfo, handleLogout }) {
  /* TODO : props로 받은 유저정보를 화면에 표시하세요. */
  if (userinfo === undefined) {
    return null;
  } else {
    return (
      <div>
        <center>
          <h1>Mypage</h1>
          <div className="username">{userinfo.username}</div>
          <div className="email">{userinfo.email}</div>
          <div className="mobile">{userinfo.mobile}</div>
          <button className="btn btn-logout" onClick={handleLogout}>
            logout
          </button>
        </center>
      </div>
    );
  }
}

export default Mypage;
