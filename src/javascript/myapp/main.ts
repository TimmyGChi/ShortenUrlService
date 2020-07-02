// Import Angular 2 and application modules.
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { ApplicationModule } from "./application.module";
import { enableProdMode } from '@angular/core';

if (webpack.ENV === 'production') {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(ApplicationModule);
