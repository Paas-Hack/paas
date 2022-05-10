import React, {useState} from 'react';
import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';  
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import RecordVoiceOverIcon from '@mui/icons-material/RecordVoiceOver';
import { useNavigate } from 'react-router-dom';

export default function HomeSearchInput() {
  const navigate = useNavigate();
  const [ inputName, setInputName ] = useState('');


  const navigatePage = (action:string) => {
    sessionStorage.setItem('inputName', inputName);
    if(action === 'record'){
      navigate('/record');
    } else {
      navigate('/result');
    }
  }
  return (
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
      <IconButton disabled={!inputName} sx={{ p: '10px', color: 'black' }} aria-label="search" onClick={()=>navigatePage('search')}>
        <SearchIcon />
      </IconButton>
      <Divider sx={{ height: 28, m: 0.5 }} orientation="vertical" />
      <IconButton disabled={!inputName} color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" onClick={()=>navigatePage('record')}>
        <RecordVoiceOverIcon />
      </IconButton>
    </Paper>
  );
}