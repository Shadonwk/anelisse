class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}


        "/"(view:"/principal/Principal")
		"500"(view:'/error')
	}
}
