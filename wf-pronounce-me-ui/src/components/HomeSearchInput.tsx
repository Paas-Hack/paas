import React, {useState} from 'react';
import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';  
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import LaunchIcon from '@mui/icons-material/Launch';
import SettingsVoiceIcon from '@mui/icons-material/SettingsVoice';
import { useNavigate } from 'react-router-dom';
import ApiService from './ApiService';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

  interface tableRow {
  	uid: any,
  	fullName: any
  	};
  	
export default function HomeSearchInput() {
  const navigate = useNavigate();

  const results : Array<tableRow> = [];
  const [ inputName, setInputName ] = useState('');
  const [ searchResults, setSearchResults ] = useState(results);


  const searchUsers = () => {
    sessionStorage.setItem('inputName', inputName);
   	ApiService.findUsers(inputName)
		.then((res:any) => { 
	    console.log('---findUsers res---', res);
	    setSearchResults(res.data || []);
	  })
		.catch((err:any)=> { 
	    console.log('---error---', err); 
	  });
  }
  
  const openUser = (rowData: any) => {
  	console.log('--rowData--', rowData);
  	sessionStorage.setItem('userData', rowData);
  	navigate('/result');
  }
  
  
  
  return (
  <>
    <Paper
      component="form"
      sx={{ p: '2px 4px', display: 'flex', alignItems: 'center', width: '50%', margin:'auto' }}
    >
      <InputBase
        sx={{ ml: 1, flex: 1 }}
        placeholder="Enter the Name"
        inputProps={{ 'aria-label': 'Enter the Name' }}
        value={inputName}
        onChange={(val:any) => setInputName(val?.target?.value)}
      />
      <IconButton disabled={!inputName} sx={{ p: '10px', color: 'black' }} aria-label="search" onClick={()=>searchUsers()}>
        <SearchIcon />
      </IconButton>
    </Paper>
    
    { searchResults && searchResults.length > 0 && 
    <TableContainer className="tableResults" component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="results table">
        <TableHead>
          <TableRow>
            <TableCell>User ID</TableCell>
            <TableCell align="right">Full Name</TableCell>
            <TableCell align="right">User Pronunciation Opted</TableCell>
            <TableCell align="right">Custom Recordings</TableCell>
            <TableCell align="right">Open</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {searchResults.map((row) => (
            <TableRow
              key={row.uid}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell align="right">{row.uid}</TableCell>
              <TableCell align="right">{row.fullName}</TableCell>
              <TableCell align="right">Y</TableCell>
              <TableCell align="right">Y</TableCell>
              <TableCell align="right"><LaunchIcon onClick={()=>openUser(row)}/></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer> }
    </>
  );
}