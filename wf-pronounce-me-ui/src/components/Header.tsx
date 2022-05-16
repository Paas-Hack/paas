import React, { Component, useEffect } from 'react';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { useNavigate, useLocation } from 'react-router-dom';
import ProunceNamesLogo from '../images/logo.png';



interface TitleProps {
}

function LinkTab(props: any) {
    const navigate = useNavigate();
    return (
      <Tab
        component="a"
        onClick={(event: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
          event.preventDefault();
          navigate(props.routename);
        }}
        {...props}
      />
    );
  }

class Header extends Component<TitleProps> {

    state = {
        tabValue: 0,
        intervalId:0,
        showTabs: false
    }

    componentDidMount() { 	
    	sessionStorage.clear();
        const intervalId = setInterval(()=>{
            const userData =  sessionStorage.getItem('userData') || '';
            this.setState({showTabs: !!userData });
            const locHref = window.location.href;
            if(!locHref.includes('#/login') && !userData){
                window.location.href = locHref.split('#')[0]+'#/login';
            }
        },1000);
        this.setState({ intervalId: intervalId });
    }

    

    componentWillUnmount(){
        sessionStorage.clear();
        clearInterval(this.state.intervalId); 
    }

    handleChange = (event: React.SyntheticEvent, newValue: number) => {
        this.setState({tabValue: newValue});
    };


    render() { 
        return (
            <>
                <div className="wfLogoStripParent">
                    <div className="wfLogoStripChild">
                        <div id="brand">
                            <a href="/"><img src={ProunceNamesLogo} alt="PaaS Home Page" role="img" /></a>
                        </div>
                        { this.state?.showTabs && <Tabs value={this.state.tabValue} onChange={this.handleChange} aria-label="Nav Tabs">
                            <LinkTab label="Search" routename="/home" />
                            <LinkTab label="Profile" routename="/result" />
                        </Tabs> }
                    </div>
                </div>
            </>
        );
    }
}

export default Header;