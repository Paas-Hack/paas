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
import ApiService from './ApiService';


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
    expanded: false,
    loggedInUserData: this.userObj
  }

  componentDidMount() { 	
  	const loggedInUserData =  JSON.parse(sessionStorage.getItem('loggedInUserData') || '{}');
    const userData =  JSON.parse(sessionStorage.getItem('userData') || '{}');
    setTimeout(()=>this.setState({userObj: userData, loggedInUserData: loggedInUserData }),1);
    console.log('-----onLoad---', this.state.userObj);
    if(userData.uid){
    	this.getUserRecordings(userData.uid);
    }    
  }
  
  
    getUserRecordings = (uId:any) => {
	  ApiService.getUserRecordings(uId)
    	.then((res:any) => { 
        console.log('---getUserRecordings res---', res);
      })
    	.catch((err:any)=> { 
        console.log('---error---', err); 
      });
  };

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
          title={this.state?.userObj?.email}
          subheader={this.state?.userObj?.lanId}
        />
          <div className="pronounciationDiv">
            <div className="resultOne">
              <div className="details">
                <strong>Standard</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.fullName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.standardPhoneticName}</Typography>
              </div>   
              <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('standardAudioRef')}>
                <RecordVoiceOverIcon />
              </IconButton>    
              {this.state?.userObj?.standardName && 
                <audio id="standardAudioRef" src={"/paas/file/standard/"+this.state.userObj.standardName}></audio> }
            </div>
          { this.state?.userObj?.preferredPronounciation && <div className="resultTwo">
              <div className="details">
                <strong>Preferred</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.preferredName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.preferredPhoneticName}</Typography>
              </div>       
              <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('preferredAudioRef')}>
                {this.state?.userObj?.preferredName && 
                    <audio id="standardAudioRef" src={"paas/file/preferred/"+this.state.userObj.preferredName}></audio> }
              </IconButton>    
              {this.state?.userObj?.standardName && 
                <audio id="standardAudioRef" src={"/paas/file/standard/"+this.state.userObj.standardName}></audio> }
            </div>}
          </div>
        <CardContent>
          <Typography variant="body2" color="text.secondary"><strong>First Name</strong>{this.state?.userObj?.firstName}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Last Name</strong>{this.state?.userObj?.lastName}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Date Of Joining</strong>{this.state?.userObj?.doj}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Work Location</strong>{this.state?.userObj?.workLoc}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Phone Number</strong>{this.state?.userObj?.phone}</Typography>
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