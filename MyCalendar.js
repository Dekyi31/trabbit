import React from 'react';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import format from 'date-fns/format';
import startOfWeek from 'date-fns/startOfWeek';
import getDay from 'date-fns/getDay';
import { enUS } from 'date-fns/locale';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const locales = {
  'en-US': enUS
};

const localizer = dateFnsLocalizer({
  format,
  startOfWeek,
  getDay,
  locales
});

const MyCalendar = ({ events }) => (
  <div style={{ height: 700 }}>
    <Calendar
      localizer={localizer}
      events={events}
      startAccessor="start"
      endAccessor="end"
    />
  </div>
);

export default MyCalendar;

// import React, { useState, useEffect } from 'react';
// import FullCalendar from '@fullcalendar/react';
// import dayGridPlugin from '@fullcalendar/daygrid';
// import axios from 'axios';

// const CalendarComponent = () => {
//   const [events, setEvents] = useState([]);

//   useEffect(() => {
//     const fetchUserTasks = async () => {
//       const userId = 'user_id'; // Replace with the actual user ID

//       try {
//         const response = await axios.get(`/api/alltasks/${userId}`);
//         const userTasks = response.data;

//         // Transform userTasks into FullCalendar events format
//         const formattedEvents = userTasks.map((task) => ({
//           title: task.name,
//           start: task.startDate, // Replace with the task start date property
//           end: task.endDate, // Replace with the task end date property
//         }));

//         setEvents(formattedEvents);
//       } catch (error) {
//         console.error('Error fetching user tasks:', error);
//       }
//     };

//     fetchUserTasks();
//   }, []);

//   return (
//     <div>
//       <h1>User Calendar</h1>
//       <FullCalendar
//         plugins={[dayGridPlugin]}
//         initialView="dayGridMonth"
//         events={events}
//       />
//     </div>
//   );
// };

// export default CalendarComponent;
