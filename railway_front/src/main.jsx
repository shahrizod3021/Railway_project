import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer} from 'react-toastify';
import  '../src/assets/css/Media.css'
import * as mdb from 'mdb-ui-kit';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
      <ToastContainer />
  </React.StrictMode>,
)
