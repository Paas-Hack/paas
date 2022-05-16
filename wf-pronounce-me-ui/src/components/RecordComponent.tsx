import React, { Component } from 'react';
// @ts-ignore
import { Recorder } from 'react-voice-recorder';
import 'react-voice-recorder/dist/index.css'; 
import ApiService from './ApiService';

class RecordComponent extends Component<any> { 
	  userObj: any = {};
	  state = {
	    userObj: this.userObj,
        audioDetails: {
            url: null,
            blob: null,
            chunks: null,
            duration: {
                h: null,
                m: null,
                s: null,
            }
        }
    }
    inputRef:any = React.createRef();

    componentDidMount(){ 
        const userData =  JSON.parse(sessionStorage.getItem('userData') || '{}');
        setTimeout(()=>this.setState({userObj: userData }),1);
    }

    
    handleAudioStop = (data: any) => {
        console.log(data)
        this.setState({ audioDetails: data });
    }
    
    handleAudioUpload = (file: any) => {
        console.log(file, this.state.audioDetails.blob);
        if(!file){
            this.inputRef.current.click();
        }
        const formData = new FormData();
        formData.append("username", this.state.userObj.uId);
        formData.append("file", file);
        formData.append("isPrimary", true+"");

	        ApiService.uploadUserRecordings(this.state.userObj.uid, formData)
		    	.then((res:any) => {
			        console.log('---getUserRecordings res---', res.data);
			      })
		    	.catch((err:any)=> { 
		        console.log('---error---', err); 
		      });
    }
    
    handleOnChange = (value: any, name: any) => {
        console.log(value, name);
        
    }

    handleReset = () => {
        const reset = {
            url: null,
            blob: null,
            chunks: null,
            duration: {
                h: null,
                m: null,
                s: null,
            }
        }
        this.setState({ audioDetails: reset });
    }

    render() {

        return (
            <div className="recordingContainer">
                <Recorder
                    record={true}
                    title={ <span>{"\""+this.state?.userObj?.fullName+ "\" Recording"}</span>}
                    audioURL={this.state.audioDetails.url}
                    showUIAudio
                    handleAudioStop={(data:any) => this.handleAudioStop(data)}
                    handleOnChange={(value:any) => this.handleOnChange(value, 'firstname')}
                    handleAudioUpload={(data:any) => this.handleAudioUpload(data)}
                    handleReset={() => this.handleReset()} />
                    <input type="file" hidden ref={this.inputRef} onChange={(data:any)=>this.handleAudioUpload(data)}></input> 
            </div>
        );
    }
}

export default RecordComponent;