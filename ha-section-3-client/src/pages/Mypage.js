import React from 'react';
import axios from 'axios';

axios.defaults.withCredentials = true;

function Mypage({ userinfo, handleLogout }) {
  /* TODO : props로 받은 유저정보를 화면에 표시하세요. */
<<<<<<< HEAD
  if (userinfo === null) return null;

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
=======
  if (userinfo === null) {
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
>>>>>>> b251dd8dcbe78e4b379c2059356c72f0fd28ec8f
}

export default Mypage;
