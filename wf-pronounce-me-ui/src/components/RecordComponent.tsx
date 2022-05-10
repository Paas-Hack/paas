import React, { Component } from 'react';
// @ts-ignore
import { Recorder } from 'react-voice-recorder';
import 'react-voice-recorder/dist/index.css'; 


class RecordComponent extends Component<any> { 
    state = {
        inputName:'',
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
        const inputName =  sessionStorage.getItem('inputName') || '';
        setTimeout(()=>this.setState({inputName:inputName}),1);
    }

    
    handleAudioStop = (data: any) => {
        console.log(data)
        this.setState({ audioDetails: data });
    }
    handleAudioUpload = (file: any) => {
        console.log(file);
        if(!file){
            this.inputRef.current.click();
        }
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
                    title={ <span>{"\""+this.state?.inputName+ "\" Recording"}</span>}
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