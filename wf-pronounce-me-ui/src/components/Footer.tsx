import React, { Component } from 'react';

interface TitleProps {
    title?: string;
    subtitle?: string;
    children?: any;
}

class Footer extends Component < TitleProps > {
    render() {
        const { title, subtitle, children } = this.props;
        return (
            <>
                <footer role="contentinfo">
                    <div className="html5footer c9" id="pageFooter">
                        <div className="c9content">
                            <nav role="navigation" aria-label="corporate, legal, security">
                                <div className="html5nav">
                                    <ul className="navList">
                                        <li className="liFirst">
                                            <a href="void(0)">Home</a>
                                        </li>
                                        <li>
                                            <a href="void(0)">Notice of Data Collection</a>
                                        </li>
                                        <li>
                                            <a href="void(0)">General Terms of Use</a>
                                        </li>
                                        <li>
                                            <a href="void(0)">Sitemap</a>
                                        </li>
                                        <li>
                                            <a href="void(0)">About PronounceNames</a>
                                        </li>
                                    </ul>
                                </div>
                            </nav>
                            <p>© 2022 Pronounce Names. All rights reserved.</p>
                        </div>
                    </div>
                </footer>
            </>
        );
    }
}

export default Footer;