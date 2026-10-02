import React, { FC, ReactNode, ReactElement } from "react";
import { BackTop } from "antd";

import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

interface AppLayoutProps {
    children: ReactNode;
}

/**
 * Provides the shell layout shared by the application pages.
 *
 * @param children - Content rendered between the navigation and footer.
 * @returns A layout component containing the shared UI chrome.
 */
const AppLayout: FC<AppLayoutProps> = ({ children }): ReactElement => (
    <>
        <NavBar />
        {children}
        <Footer />
        <BackTop />
    </>
);

export default AppLayout;
