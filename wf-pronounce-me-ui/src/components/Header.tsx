import React, { Component } from 'react';

interface TitleProps {
    title?: string;
    subtitle?: string;
    children?: any;
}

class Header extends Component<TitleProps> {
    render() {
        const { title, subtitle, children } = this.props;
        return (
            <>
                <div className="wfLogoStripParent">
                    <div className="wfLogoStripChild">
                        <div id="brand">
                            <a href="/"><img src="https://www01.wellsfargomedia.com/assets/images/css/template/homepage/homepage-horz-logo.svg" alt="Wells Fargo Home Page" role="img" /></a>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default Header;