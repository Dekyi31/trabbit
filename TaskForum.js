import React, { useState } from 'react';
import api from '../api/axiosConfig';

const TaskForm = ({username}) => {
  const [title, setTitle] = useState('');
  const [start, setStart] = useState('');
  const [end, setEnd] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post(`users/create`, {
        username: username,
        description: title,
        date: start,
        dueDate: end,
      });

      console.log(response.data);
    } catch (err) {
      console.error(err.response.data);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Task title" required />
      <input type="datetime-local" value={start} onChange={(e) => setStart(e.target.value)} required />
      <input type="datetime-local" value={end} onChange={(e) => setEnd(e.target.value)} required />
      <button type="submit">Create Task</button>
    </form>
  );
};

export default TaskForm;