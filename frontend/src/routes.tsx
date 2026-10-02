import { ComponentType, LazyExoticComponent, lazy } from "react";

import {
    BASE,
    CART,
    CONTACTS,
    FORGOT,
    LOGIN,
    MENU,
    ORDER,
    ORDER_FINALIZE,
    PRODUCT,
    REGISTRATION,
    RESET,
    ACTIVATE,
} from "./constants/routeConstants";

interface RouteDefinition {
    path: string;
    exact?: boolean;
    component: LazyExoticComponent<ComponentType<any>>;
}

/**
 * Route configuration used by the application shell.
 * Page imports are lazy-loaded to improve initial bundle size and page performance.
 */
export const routes: RouteDefinition[] = [
    { path: BASE, exact: true, component: lazy(() => import("./pages/Home/Home")) },
    { path: LOGIN, exact: true, component: lazy(() => import("./pages/Login/Login")) },
    { path: REGISTRATION, exact: true, component: lazy(() => import("./pages/Registration/Registration")) },
    { path: FORGOT, exact: true, component: lazy(() => import("./pages/ForgotPassword/ForgotPassword")) },
    { path: `${RESET}/:code`, exact: true, component: lazy(() => import("./pages/ResetPassword/ResetPassword")) },
    { path: `${ACTIVATE}/:code`, exact: true, component: lazy(() => import("./pages/Login/Login")) },
    { path: MENU, exact: true, component: lazy(() => import("./pages/Menu/Menu")) },
    { path: `${PRODUCT}/:id`, exact: true, component: lazy(() => import("./pages/Product/Product")) },
    { path: CONTACTS, exact: true, component: lazy(() => import("./pages/Contacts/Contacts")) },
    { path: CART, exact: true, component: lazy(() => import("./pages/Cart/Cart")) },
    { path: ORDER, exact: true, component: lazy(() => import("./pages/Order/Order")) },
    { path: ORDER_FINALIZE, exact: true, component: lazy(() => import("./pages/OrderFinalize/OrderFinalize")) },
];
