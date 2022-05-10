import React, { Component } from 'react';
import ProunceNamesLogo from '../images/pronounce_names.png';
import HomeSearchInput from './HomeSearchInput';


interface TitleProps {
    title?: string;
    subtitle?: string;
    children?: any;
}

class HomePage extends Component<TitleProps> {
    render() {
        const { title, subtitle, children } = this.props;
        return (
            <>
                <div className="homePageContent">
                    <div className="pronounceNamesImg">
                        <img src={ProunceNamesLogo} alt="Pronounce Names" role="img" />
                    </div>
                    <div className="searchBoxContainer">
                        <HomeSearchInput></HomeSearchInput>
                    </div>
                </div>
            </>
        );
    }
}

export default HomePage;