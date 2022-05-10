import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ReactAudioPlayer from 'react-audio-player';
import RecordVoiceOverIcon from '@mui/icons-material/RecordVoiceOver';
// @ts-ignore
import TestOneMp3 from '../assets/test1.mp3';

interface ExpandMoreProps extends IconButtonProps {
  expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function ResultComponent() {

  const resObj:any = {
    standardName: 'Hello World',
    standardPronounciation: 'sdgdfgdfg  dfgdfg dfgdfg dfgdfg dffgdfg dfgfdg fdgfdg'
  };
  const [expanded, setExpanded] = React.useState(false);
  const [result, setResult] = React.useState(resObj);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const playVoice = (audioTagId:any) => {
    const audioRef = document.getElementById(audioTagId);
    // @ts-ignore
    audioRef.play();
  }

  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
            R
          </Avatar>
        }
        title="Shrimp and Chorizo Paella"
        subheader="September 14, 2016"
      />
        
        <div className="resultOne">
          <div className="details">
            <strong>Standard</strong>
            <Typography variant="body2" color="text.secondary">{result?.standardName}</Typography>
            <Typography variant="body2" color="text.secondary">{result?.standardPronounciation}</Typography>
          </div>   
          <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
              onClick={()=>playVoice('standardAudioRef')}>
            <RecordVoiceOverIcon />
          </IconButton>    
          <audio id="standardAudioRef" src={TestOneMp3}></audio>
        </div>
        <br/>
        <div className="resultTwo">
          <div className="details">
            <strong>Preferred</strong>
            <Typography variant="body2" color="text.secondary">{result?.standardName}</Typography>
            <Typography variant="body2" color="text.secondary">{result?.standardPronounciation}</Typography>
          </div>       
          <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
              onClick={()=>playVoice('preferredAudioRef')}>
            <RecordVoiceOverIcon />
          </IconButton>    
          <audio id="preferredAudioRef" src={TestOneMp3}></audio>
        </div>
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          Hello World is Active
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton aria-label="Delete Pronunciation">
          <DeleteIcon />
        </IconButton>
        <IconButton aria-label="Edit Pronunciation">
          <EditIcon />
        </IconButton>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          Extra Audio Files 
        </CardContent>
      </Collapse>
    </Card>
  );
}
