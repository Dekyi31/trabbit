import './App.css';
import api from './api/axiosConfig';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import TaskForum from './taskDash/TaskForum';





// import React from 'react';
// import LoginForum from './start-page/LoginForum';
// import { Routes, Route } from 'react-router-dom';

// function App() {
//   return (
//     <Routes>
//       <Route path="/" element={<LoginForum />} />
//     </Routes>
//   );
// }


// export default App;

import React, {useState, useRef, useEffect } from 'react';
import LoginForum from './startForums/LoginForum';
import parse from 'date-fns/parse';
import { Navigate, Route, Routes } from 'react-router-dom';
import MyCalendar from './taskDash/MyCalendar';
import NavigationBar from './NavigationBar';

const App = () => {
    const usernameRef = useRef();
    const passwordRef = useRef();
    const emailRef = useRef();
    const nameRef = useRef();
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');

    useEffect(() => {
      Notification.requestPermission();
      fetchTasks();
    }, []);
    const [justifyActive, setJustifyActive] = useState('tab1');
    const [tasks, setTasks] = useState([]);

    const handleJustifyClick = (value) => {
      if (value === justifyActive) {
         return;
      }
       setJustifyActive(value);
    };

    const fetchTasks = async () => {
      try {
        const username = usernameRef.current.value;
        const response = await api.get(`users/${username}/tasks/`);
        const events = response.data.map(task => ({
          title: task.description,
          start: parse(task.startDateTime, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx", new Date()),
          end: parse(task.endDateTime, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx", new Date()),
        }));
        setTasks(events);
      } catch (err) {
        console.error(err);
      }
    };
  
    const register = async (e) => {
      e.preventDefault();
      const name = nameRef.current.value;
      const username = usernameRef.current.value;
      const password = passwordRef.current.value;
      const email = emailRef.current.value;
      try {
        const response = await api.post("/api/auth/register", {
          name: name,
          username: username,
          email: email,
          password: password
        });
        if (response.status === 200) {
          localStorage.setItem('token', response.data.token);
          setUsername(username);
          setIsLoggedIn(true);
          fetchTasks();
        }
      } catch (err) {
        console.error(err);
      }
    };

    const login = async (e) => {
      e.preventDefault();
      const username = usernameRef.current.value;
      const password = passwordRef.current.value;
  
      try {
        const response = await api.post("/api/auth/login", {
          username: username,
          password: password
        });
        if (response.status === 200) {
          localStorage.setItem('token', response.data.token);
          setUsername(username);
          setIsLoggedIn(true);
          fetchTasks();
        }
        console.log(response);
      } catch (err) {
        console.error(err);
      }
    };
  
    return (
<>
      {!isLoggedIn ? (
        <LoginForum
          handleJustifyClick={handleJustifyClick}
          handleSubmit={login}
          handleRegister={register}
          nameRef={nameRef}
          usernameRef={usernameRef}
          emailRef={emailRef}
          passwordRef={passwordRef}
          justifyActive={justifyActive}
        />
          ) : (
            <>
              <TaskForum username={username}/>
              <MyCalendar events={tasks} />
            </>
          )}
        </>
      );
};

export default App;
