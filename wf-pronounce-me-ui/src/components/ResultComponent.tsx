import React, { Component } from 'react';
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
import PersonIcon from '@mui/icons-material/Person';
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

class ResultComponent extends Component<any> { 
  userObj: any = {};
  state = {
    userObj: this.userObj,
    expanded: false
  }

  componentDidMount() { 
    const inputName =  sessionStorage.getItem('inputName') || '';
    setTimeout(()=>this.setState({userObj:{ standardName: inputName}}),1);
    console.log('-----onLoad---', inputName, this.state.userObj);
  }

  handleExpandClick = () => {
    this.setState({expanded:!this.state.expanded});
  };

  playVoice = (audioTagId:any) => {
    const audioRef = document.getElementById(audioTagId);
    // @ts-ignore
    audioRef.play();
  }

  render() {
    return (
      <Card sx={{ maxWidth: 345 }}>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
              <PersonIcon />
            </Avatar>
          }
          title="Shrimp and Chorizo Paella"
          subheader="September 14, 2016"
        />
          <div className="pronounciationDiv">
            <div className="resultOne">
              <div className="details">
                <strong>Standard</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.standardName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.standardPronounciation}</Typography>
              </div>   
              <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('standardAudioRef')}>
                <RecordVoiceOverIcon />
              </IconButton>    
              {this.state?.userObj?.standardName && 
                <audio id="standardAudioRef" src={"paas/file/standard/"+this.state.userObj.standardName}></audio> }
            </div>
          { this.state?.userObj?.preferredPronounciation && <div className="resultTwo">
              <div className="details">
                <strong>Preferred</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.preferredName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.preferredPronounciation}</Typography>
              </div>       
              <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('preferredAudioRef')}>
                {this.state?.userObj?.preferredName && 
                    <audio id="standardAudioRef" src={"paas/file/preferred/"+this.state.userObj.preferredName}></audio> }
              </IconButton>    
              <audio id="preferredAudioRef" src={TestOneMp3}></audio>
            </div>}
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
            expand={this.state?.expanded}
            onClick={this.handleExpandClick}
            aria-expanded={this.state?.expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
        <Collapse in={this.state?.expanded} timeout="auto" unmountOnExit>
          <CardContent>
            Extra Audio Files 
          </CardContent>
        </Collapse>
      </Card>
    );
  };
}
export default ResultComponent;