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
import Switch from '@mui/material/Switch';
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
    loggedInUserData: this.userObj,
    standardRecordingUrl: '',
    preferredRecordingUrl: '',
    preferredRecording: this.userObj
  }

  componentDidMount() { 	
  	const loggedInUserData =  JSON.parse(sessionStorage.getItem('loggedInUserData') || '{}');
    const userData =  JSON.parse(sessionStorage.getItem('userData') || '{}');
    setTimeout(()=>this.setState({userObj: userData, loggedInUserData: loggedInUserData }),1);
    console.log('-----onLoad---', this.state.userObj);
    if(userData.uid){
    	this.getUserRecordings(userData.uid);
    	this.getRecording(userData.fullName);
    }
    
    /* if (!window.AudioContext) {
      if (!window.webkitAudioContext) {
        alert("Your browser does not support any AudioContext and cannot play back this audio.");
        return;
      }
      window.AudioContext = window.webkitAudioContext;
    }
    this.context = new AudioContext();    */
  }
  
  
    getUserRecordings = (uId:any) => {
	  ApiService.getUserRecordings(uId)
    	.then((res:any) => {
        setTimeout(()=>this.setState({preferredRecording: res.data[0], preferredRecordingUrl: 'data:audio/wav;base64,'+res.data[0].data }),1);
        console.log('---getUserRecordings res---', res.data[0]);
      })
    	.catch((err:any)=> { 
        console.log('---error---', err); 
      });
    };
    
    getRecording = (userName: any) => {
	  ApiService.getRecording(userName)
    	.then((res:any) => { 
        console.log('---getUserRecordings res---', res);
        setTimeout(()=>this.setState({standardRecordingUrl: window.URL.createObjectURL(new Blob([res.data])) }),1);
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
    
    if(audioTagId === 'preferredAudioRef'){
     	// @ts-ignore
    	audioRef.src = this.state.preferredRecordingUrl;
    }
    // @ts-ignore
    audioRef.play();
  }
  
  navigatePage = (route:any) => {
 	 window.location.href = window.location.href.replace('/result', '/'+route);
  }
  
  toggleSwitch = (switchVal: any) => {
  	console.log('----switchVal---', switchVal);
  }
  
  
  /* 
  playByteArray = (byteArray) => {
    const arrayBuffer = new ArrayBuffer(byteArray.length);
    const bufferView = new Uint8Array(arrayBuffer);
    for (i = 0; i < byteArray.length; i++) {
      bufferView[i] = byteArray[i];
    }
    context.decodeAudioData(arrayBuffer, function(buffer) {
        const buf = buffer;
        this.play(buf);
    });
  }
 
 
	 play = (buf: any) => {
	    const source = this.context.createBufferSource();
	    source.buffer = buf;
	    source.connect(this.context.destination);
	    source.start(0);
	 }
	 */

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
        >
        <Switch defaultChecked color="default" onChange={this.toggleSwitch} />
        </CardHeader>
          <div className="pronounciationDiv">
            <div className="resultOne">
              <div className="details">
                <strong>Standard</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.fullName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.standardPhoneticName}</Typography>
              </div>   
              {this.state?.userObj?.fullName && this.state.standardRecordingUrl && 
              <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('standardAudioRef')}>
                <RecordVoiceOverIcon />
                <audio id="standardAudioRef" src={this.state.standardRecordingUrl}></audio>
              </IconButton>  }
            </div>
          { this.state?.preferredRecording?.data && <div className="resultTwo">
              <div className="details">
                <strong>Preferred</strong>
                <Typography variant="body2" color="text.secondary">{this.state?.preferredRecording?.phoneticString || this.state?.userObj?.fullName}</Typography>
                <Typography variant="body2" color="text.secondary">{this.state?.userObj?.preferredPhoneticName}</Typography>
              </div>       
               {this.state?.preferredRecordingUrl && 
               <IconButton color="primary" sx={{ p: '10px', color: 'black' }} aria-label="RecordVoice" 
                  onClick={()=>this.playVoice('preferredAudioRef')}>
                   <RecordVoiceOverIcon />
                    <audio id="preferredAudioRef" src={this.state?.preferredRecordingUrl}></audio> 
              </IconButton>  }
                
            </div>}
          </div>
        <CardContent>
          <Typography variant="body2" color="text.secondary"><strong>First Name  </strong>{this.state?.userObj?.firstName}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Last Name  </strong>{this.state?.userObj?.lastName}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Date Of Joining  </strong>{this.state?.userObj?.doj}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Work Location  </strong>{this.state?.userObj?.workLoc}</Typography>
          <Typography variant="body2" color="text.secondary"><strong>Phone Number  </strong>{this.state?.userObj?.phone}</Typography>
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label="Delete Pronunciation">
            <DeleteIcon />
          </IconButton>
          <IconButton aria-label="Edit Pronunciation" onClick={()=>this.navigatePage('record')}>
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