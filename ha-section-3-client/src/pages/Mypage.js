import React from 'react';
import axios from 'axios';

axios.defaults.withCredentials = true;

function Mypage(props) {
  /* TODO : props로 받은 유저정보를 화면에 표시하세요. */
  return (
    <div>
      <center>
        <h1>Mypage</h1>
        <div className="username">{props.username}</div>
        <div className="email">{props.email}</div>
        <div className="mobile">{props.mobile}</div>
        {/* <button className="btn btn-logout" onClick={handleLogout}> */}
        {/* logout */}
        {/* </button> */}
      </center>
    </div>
  );
}

export default Mypage;
